<template>
  <div>
    <button class="btn btn-primary" @click="onClickAddr">Test</button>

    <input type="checkbox" class="my-5">
    <p>hi</p>
    <input type="file" id="recipe_thumbnail" class="my-5">

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

    <div v-show="tempInputs.length > 0" class="upload-image">
      <draggable v-model="tempInputs" group="recipeDetail" @start="drag=true" @end="drag=false">
        <div v-for="(tempInput, index) in tempInputs" :key="index" class="row my-3">
          <img :src="tempInput.imageSrc" class="col-4 offset-1">
          <input type="text" v-model="tempInput.content" class="col-3">
          <p class="col-3">{{ tempInput.rawFile.name }}</p>  
        </div>
      </draggable>
    </div>

    <!-- <ul v-show="tempInputs.length > 0" class="upload-image">
      <li v-for="(tempInput, index) in tempInputs" :key="index" class="row my-3">
        <img :src="tempInput.imageSrc" class="col-4 offset-1">
        <input type="text" v-model="tempInput.content" class="col-6">
      </li>
    </ul> -->
    <button class="btn btn-danger" @click="makeRecipe">제출 실험</button>
  </div>
</template>

<!-- 주소 검색 API, 위치확인 필요 -->
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
import draggable from 'vuedraggable'

import axios from "axios";
import SERVER from "@/api/api";
import { mapGetters } from "vuex";

export default {
  name: 'About',
  data () {
      return {
        filename: '',
        addingFiles: [],
        fileList: [],
        tempInputs: [],
      }
  },
  components: {
    draggable,
  },
  computed: {
    ...mapGetters(["config"]),
  },
  methods: {
    onClickAddr() {
      const info = this
      new daum.Postcode({
        oncomplete(data) {
        var roadAddr = data.roadAddress;
        var extraRoadAddr = ''
        if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
          extraRoadAddr += data.bname
        }
        if (data.buildingName !== '' && data.apartment === 'Y') {
          extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName)
        }
        if (extraRoadAddr !== '') {
          extraRoadAddr = ' (' + extraRoadAddr + ')'
        }
        console.log(data.zonecode)
        console.log(roadAddr)
        }
      }).open()
    },
    
    onDrop (event) {
      this.inputImageFile(event.dataTransfer.files)
    },
    onClickFile(event) {
      this.$refs.fileInput.click()
    },
    onFileChange(event) {
      this.inputImageFile(event.target.files)
    },


    inputImageFile (addingfiles) {
      console.log(addingfiles)
      this.addingFiles = addingfiles
      this.fileList = [...this.fileList, ...addingfiles]
      console.log(this.fileList)
      this.onClickUpload()
    },
    onClickUpload () {
      const self = this
      this.addingFiles.forEach(function(file) {
        self.tempInputs.push(
          {'imageSrc': URL.createObjectURL(file), 'content': null, 'rawFile': file}
          )
      })
    },
    makeRecipe() {
      let frm = new FormData();
      let photoFile = document.getElementById("recipe_thumbnail");
      frm.append("recipeThumbnail", photoFile.files[0]);

      frm.append("recipeName", "테스트용 레시피");
      frm.append("recipeDetail", "여러가지 실험 중입니당 ㅎㅎ");

      // 요리 난이도, 요리 소요 시간도 String으로 보내줍니다.
      frm.append("level", 4);
      frm.append("cookTime", 17);

      // categories 배열에는 선택된 카테고리의 ID가 String으로 들어갑니다. / 숫자로 넣는건 테스트를 해봐야됩니다.abnf
      // 아래 반복문을 이렇게 고쳐보세요 frm.append('categories', selectedCategoryId * 1);
      [1, 11].forEach(function (selectedCategory) {
        frm.append("categories", selectedCategory);
      });

      // 재료의 경우 ingredientPk, ingredientAmount 2개의 배열로 나뉘어서 들어갑니다.
      frm.append("ingredientPk", 1);
      frm.append("ingredientAmount", 100);

      // 상세 정보의 경우에도 description 배열에 String으로 들어갑니다.
      this.tempInputs.forEach(function (tempInput) {
        frm.append("description", tempInput.content);
        frm.append("photo", tempInput.rawFile);
      });

      let configs = {
        headers: {
          "Content-Type": "multipart/form-data",
          Authorization: this.config,
        },
      };

      axios
        .post(SERVER.URL + SERVER.ROUTES.recipeRegister, frm, configs)
        .then((res) => {
          console.log(res);
          router.push({ name: "RecipeListView", params: { pageNum: 1 } });
        })
        .catch((err) => {
          console.log(err);
        });
    },
  }
}
</script>
<style>

</style>