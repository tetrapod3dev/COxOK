<template>
  <div class="container">
    <img :src="imageSrc(recipe.recipeThumbnailSrc)">
    
    
    <h2>상세 과정 입력</h2>

    <div class="upload-image">
      <draggable v-model="recipe.recipePhotoList" group="recipeDetail" @start="drag=true" @end="drag=false">
        <div v-for="(recipePhoto, index) in recipe.recipePhotoList" :key="index" class="row my-3">

          <div class="col-4 offset-1 row">
            <img v-if="recipePhoto.rawFile" :src="recipePhoto.photoSrc">
            <img v-else :src="imageSrc(recipePhoto.photoSrc)">
            <input type="file" @change="changeFile(index, $event)">
          </div>

          <input type="text" v-model="recipePhoto.photoDetail" class="col-5">
          <button class="btn btn-danger col-1" @click="removeRecipeDetail(index)">-</button>
        </div>
      </draggable>
    </div>

    <div class="input-group">
      <input
        type="text"
        class="form-control"
        placeholder="드래그 & 드랍하거나 인풋을 클릭하세용"
        v-model="filename"
        @dragover.prevent
        @drop.prevent="onDrop"
        multiple>
      <div class="input-group-append">
        <span class="input-group-text" @click="onClickFile">
          <i class="fa fa-paperclip" />
        </span>
      </div>
      <input hidden type=file class="file-input" ref="fileInput" multiple @change="onFileChange">
    </div>

    <button @click="submitUpdate">Test</button>

  </div>
</template>

<script>
import axios from 'axios'
import SERVER from '@/api/api'
import {mapGetters} from 'vuex'

import draggable from 'vuedraggable'

export default {
  name: 'RecipeUpdateView',
  data() {
    return {
      recipe: {},
      
      filename: '',
      addingFiles: [],
      fileList: [],
      tempInputs: [],

      tempFilesSrc: [],
      categories: {},
      ingredients: {},
    }
  },
  computed: {
    ...mapGetters(['config']),
    photoSrcs() {
      return this.recipe.recipePhotoList.map(recipePhoto => recipePhoto.photoSrc)
    }
  },
  components: {
    draggable,
  },
  created() {
    axios
      .get(
        SERVER.URL +
          SERVER.ROUTES.updateInfo +
          this.$route.params.recipe_id,
          {
            headers: {
              Authorization: this.config,
            },
          }
        )
      .then((res) => {
        this.recipe = res.data.recipe
        console.log(res.data.recipe)
      })
      .catch((err) => console.log(err.response));
      
    this.getCategory()
  },
  methods: {
    changeFile(index, event) {
      const file = event.target.files[0]
      this.recipe.recipePhotoList[index].photoSrc = URL.createObjectURL(file)
      this.recipe.recipePhotoList[index].rawFile = file
    },
    getCategory() {
      axios
        .get(SERVER.URL + SERVER.ROUTES.goRegister)
        .then((res) => {
          this.categories = res.data.catogories;
          const self = this;
          res.data.ingredients.forEach(function (ingredient) {
            self.ingredients[ingredient.name] = [
              ingredient.ingredientId,
              ingredient.unit,
            ];
          });
          this.ingredientsName = Object.keys(this.ingredients);
        })
        .catch((err) => console.log(err));
    },

    imageSrc(fileSrc) {
      return SERVER.URL + '/img/' + fileSrc
    },
    
    // 레시피 상세 과정 관련 함수
    onDrop (event) {
      this.inputImageFile(event.dataTransfer.files)
    },
    onClickFile() {
      this.$refs.fileInput.click()
    },
    onFileChange(event) {
      this.inputImageFile(event.target.files)
    },
    inputImageFile (addingfiles) {
      this.addingFiles = addingfiles
      this.onClickUpload()
    },
    onClickUpload () {
      const self = this
      this.addingFiles.forEach(function(file) {
        self.recipe.recipePhotoList.push(
          {'photoSrc': URL.createObjectURL(file), 'photoDetail': null, 'rawFile': file}
          )
      })
    },
    removeRecipeDetail(index) {
      this.recipe.recipePhotoList.splice(index, 1);
    },

    TestBtn() {
      // 새로운 사진을 등록한 경우 해당 파일들을 formData에 push합니다.
      let frm = new FormData();
      this.recipe.recipePhotoList.forEach(function(recipePhoto) {
        if (recipePhoto.rawFile != undefined) {
          frm.append("photo", recipePhoto.rawFile);
        }
      })

      // formData를 API에 전달해서 src 주소를 받습니다..
      // 그리고 그걸 recipe에 저장합니다.
      const self = this
      axios
        .post(SERVER.URL + SERVER.ROUTES.photoRegister, frm, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        })
        .then((res) => {
          let tempSrcs = res.data.photo
          self.recipe.recipePhotoList.forEach(function(recipePhoto) {
            if (recipePhoto.rawFile != undefined) {
              recipePhoto.photoSrc = tempSrcs.shift()
            }
          })
        })
        .catch((err) => {
          console.log(err);
        });
      this.submitUpdate()
    },
    submitUpdate() {
      let body = {
        userId: parseInt(this.recipe.userId),
        recipeId: parseInt(this.$route.params.recipe_id),
        recipeName: '테스트중입니다.',
        recipeDetail: '제 마음대로 바꿉니다.',
        recipeThumbnail: this.recipe.recipeThumbnailSrc,
        categories: [1, 2],
        ingredientPk: [1, 2],
        ingredientAmount: [100, 200],
        description: ['실험111', '실험222', '실험333'],
        photo: this.photoSrcs,
        level: '4',
        cookTime: '16',
      }
      console.log(body)
      axios.post(SERVER.URL + SERVER.ROUTES.recipeUpdate, body, 
        {
          headers: {
            Authorization: this.config,
          },
        })
        .then(res => console.log(res))
        .catch(err => console.log(err))
    },
  }
}
</script>

<style>

</style>