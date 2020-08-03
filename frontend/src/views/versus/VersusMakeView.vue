<template>
  <div>
    <h2>요리대전 만들기 페이지입니다.</h2>

    <div>
			<label for="title">요리대전 제목</label>
			<input type="text" id="title" v-model="versusTitle">
    </div>

    <div>
			<label for="content">요리대전 설명</label>
			<input type="text" id="content" v-model="versusContent">
    </div>

    <div v-if="selectedRecipes.length == 0" class="row">
      <h2>선택한 레시피가 없습니다!</h2>
    </div>
    <div v-else class="row">
      <div v-for="recipe in selectedRecipes" :key="recipe.id" class="col-4">
        <h2>{{ recipe.title }}</h2>
      </div>
    </div>
    
    <hr>

    <div class="row">
      <button class="col-1 btn btn-outline-primary" @click="movePrev">P</button>

      <div class="col-10 row">
        <div v-for="recipe in curRecipes" :key="recipe.id" class="card col-3">
          <img :src="recipe.photo" class="card-img-top" alt="레시피 사진">
          <div class="card-body">
            <h5 class="card-title">{{ recipe.title }}</h5>
            <p class="card-text">{{ recipe.content}}</p>
          </div>
        </div>
      </div>

      <button class="col-1 btn btn-outline-primary" @click="moveNext">N</button>
    </div>

    <hr>

    <div v-for="recipe in recipes" :key="recipe.id" class="row">
      <img :src="recipe.photo" class="col-4">
      <div class="col-7">
        <h3 class="row">{{ recipe.title }} ({{ recipe.id }})</h3>
        <p class="row">{{ recipe.content }}</p>
      </div>
      <input v-if="isSelected(recipe.id)" type="checkbox" checked class="col-1" @click="checkRecipe(recipe)">
      <input v-else type="checkbox" class="col-1" @click="checkRecipe(recipe)">
    </div>


  </div>
</template>

<script>
export default {
  name: 'VersusMakeView',
  data() {
    return {
      'versusTitle': null,
      'versusContent': null,
      'selectedRecipes': [
        {'photo': 'https://dthumb-phinf.pstatic.net/?src=%22https%3A%2F%2Fdbscthumb-phinf.pstatic.net%2F0940_000_1%2F20120229113609296_U1SJ6A1TL.jpg%2Ffe15_111_i1.jpg%3Ftype%3Dw690_2%26wm%3DY%22&twidth=690&theight=524&opts=17',
        'title': '어복쟁판', 'content': '이건 뭐죠', 'id': 2}
      ],
      'recipes': [
        {'photo': 'https://dthumb-phinf.pstatic.net/?src=%22https%3A%2F%2Fdbscthumb-phinf.pstatic.net%2F2756_000_1%2F20131109003054995_MNBAW8OPM.jpg%2Fsyj_66_i1.jpg%3Ftype%3Dw690_fst_n%26wm%3DY%22&twidth=615&theight=530&opts=17',
        'title': '가지튀김', 'content': '맛있는 가지튀김', 'id': 1},
        {'photo': 'https://dthumb-phinf.pstatic.net/?src=%22https%3A%2F%2Fdbscthumb-phinf.pstatic.net%2F0940_000_1%2F20120229113609296_U1SJ6A1TL.jpg%2Ffe15_111_i1.jpg%3Ftype%3Dw690_2%26wm%3DY%22&twidth=690&theight=524&opts=17',
        'title': '어복쟁판', 'content': '이건 뭐죠', 'id': 2},
        {'photo': 'https://dthumb-phinf.pstatic.net/?src=%22https%3A%2F%2Fdbscthumb-phinf.pstatic.net%2F2756_000_1%2F20131109092023611_L604IJGG2.jpg%2Fcmp31_i1.jpg%3Ftype%3Dw690_fst_n%26wm%3DY%22&twidth=414&theight=530&opts=17',
        'title': '비어치킨', 'content': '어 이거 1박2일에서 봤어요', 'id': 3},
        {'photo': 'https://dthumb-phinf.pstatic.net/?src=%22https%3A%2F%2Fdbscthumb-phinf.pstatic.net%2F2756_000_1%2F20131109003054995_MNBAW8OPM.jpg%2Fsyj_66_i1.jpg%3Ftype%3Dw690_fst_n%26wm%3DY%22&twidth=615&theight=530&opts=17',
        'title': '가지튀김', 'content': '맛있는 가지튀김22', 'id': 4},
        {'photo': 'https://dthumb-phinf.pstatic.net/?src=%22https%3A%2F%2Fdbscthumb-phinf.pstatic.net%2F0940_000_1%2F20120229113609296_U1SJ6A1TL.jpg%2Ffe15_111_i1.jpg%3Ftype%3Dw690_2%26wm%3DY%22&twidth=690&theight=524&opts=17',
        'title': '어복쟁판', 'content': '이건 뭐죠22', 'id': 5},
        {'photo': 'https://dthumb-phinf.pstatic.net/?src=%22https%3A%2F%2Fdbscthumb-phinf.pstatic.net%2F2756_000_1%2F20131109092023611_L604IJGG2.jpg%2Fcmp31_i1.jpg%3Ftype%3Dw690_fst_n%26wm%3DY%22&twidth=414&theight=530&opts=17',
        'title': '비어치킨', 'content': '어 이거 1박2일에서 봤어요222', 'id': 6},
        {'photo': 'https://dthumb-phinf.pstatic.net/?src=%22https%3A%2F%2Fdbscthumb-phinf.pstatic.net%2F2756_000_1%2F20131109003054995_MNBAW8OPM.jpg%2Fsyj_66_i1.jpg%3Ftype%3Dw690_fst_n%26wm%3DY%22&twidth=615&theight=530&opts=17',
        'title': '가지튀김', 'content': '맛있는 가지튀김33', 'id': 7},
        {'photo': 'https://dthumb-phinf.pstatic.net/?src=%22https%3A%2F%2Fdbscthumb-phinf.pstatic.net%2F0940_000_1%2F20120229113609296_U1SJ6A1TL.jpg%2Ffe15_111_i1.jpg%3Ftype%3Dw690_2%26wm%3DY%22&twidth=690&theight=524&opts=17',
        'title': '어복쟁판', 'content': '이건 뭐죠333', 'id': 8},
        {'photo': 'https://dthumb-phinf.pstatic.net/?src=%22https%3A%2F%2Fdbscthumb-phinf.pstatic.net%2F2756_000_1%2F20131109092023611_L604IJGG2.jpg%2Fcmp31_i1.jpg%3Ftype%3Dw690_fst_n%26wm%3DY%22&twidth=414&theight=530&opts=17',
        'title': '비어치킨', 'content': '어 이거 1박2일에서 봤어요333', 'id': 9},
      ],
      'curPage': 0,
    }
  },
  computed: {
    selectedRecipesId() {
      return this.selectedRecipes.map(recipe => recipe.id)
    },
    maxPage() {
      return parseInt(this.selectedRecipes.length / 4)
    },
    curRecipes() {
      return this.selectedRecipes.slice(4*this.curPage, 4*(this.curPage+1))
    }
  },
  methods: {
    checkRecipe(recipe) {
      console.log(this.selectedRecipesId)
      if (this.selectedRecipesId.indexOf(recipe.id) == -1) {
        this.selectedRecipes.push(recipe)
      } else {
        this.selectedRecipes.splice(this.selectedRecipesId.indexOf(recipe.id), 1);
      }
    },
    isSelected(id) {
      return (this.selectedRecipesId.indexOf(id) >= 0) ? true : false
    },
    movePrev() {
      if (this.curPage != 0) {
        this.curPage -= 1
      } else {
        this.curPage = this.maxPage
      }
    },
    moveNext() {
      if (this.curPage < this.maxPage) {
        this.curPage += 1
      } else {
        this.curPage = 0
      }
    }
  }
}
</script>

<style>

</style>