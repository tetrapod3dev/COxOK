<template>
  <div>
    <CategorySelector />

    <div v-if="testC">
      <div class="toolbar">
        <button data-command='h1' @click="clickh1Tag">H1</button> 
        <button data-command='h2' @click="clickh1Tag">H2</button> 
        <button data-command='h3' @click="clickh1Tag">H3</button> 
        <button data-command='h4' @click="clickh1Tag">H4</button> 
        <button data-command='h5' @click="clickh1Tag">H5</button> 
        <button data-command='h6' @click="clickh1Tag">H6</button>

        <button @click="clickBold">Bold</button> 
        <button @click="clickItalic">Italic</button> 

        <select name="font-size" @change="changeFontSize">
          <option value="4">4</option>
          <option value="5">5</option>
          <option value="6">6</option>
          <option value="7">7</option>
        </select>
      </div>

      <div id='editor' class='editor' contenteditable="true">
        <p>에디터 테스트</p>
        <table border="1">
          <th>테이블</th>
          <th>만들기</th>
          <tr><!-- 첫번째 줄 시작 -->
              <td>첫번째 칸</td>
              <td>두번째 칸</td>
          </tr><!-- 첫번째 줄 끝 -->
          <tr><!-- 두번째 줄 시작 -->
              <td>첫번째 칸</td>
              <td>두번째 칸</td>
          </tr><!-- 두번째 줄 끝 -->
        </table>
      </div>

      <button @click="sendInput">작성 완료</button>

    </div>
  </div>
</template>

<script>

import CategorySelector from '@/components/recipes/CategorySelector.vue'

export default {
  name: 'EditorTest',
  data() {
    return {
      testC: false,
    }
  },
  components: {
    CategorySelector,
  },
  methods: {
    // https://developer.mozilla.org/ko/docs/Web/API/Document/execCommand
    clickh1Tag(event) {
      document.execCommand('formatBlock', false, event.target.dataset.command);
    },
    clickBold() {
      document.execCommand('bold');
    },
    clickItalic() {
      document.execCommand('italic');
    },
    sendInput() {
      const inputText = document.querySelector('#editor')
      console.log(inputText)
    },
    changeFontSize(event) {
      console.log(event.target.value)
      document.execCommand('fontSize', false, event.target.value)
    }
  },
  mounted() {
  }
}
</script>

<style>
font[size='4']{
  font-size: 10px;
}

font[size='5']{
  font-size: 12px;
}

font[size='6']{
  font-size: 20px;
}

font[size='7']{
  font-size: 100px;
}
</style>