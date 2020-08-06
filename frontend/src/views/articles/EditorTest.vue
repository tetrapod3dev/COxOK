<template>
  <div>
    <CategorySelector />

    <CxkEditor />
  </div>
</template>

<script>
import CategorySelector from "@/components/recipes/CategorySelector.vue";
import CxkEditor from "@/components/cxkeditor/cxkeditor.vue";

export default {
  name: "EditorTest",
  data() {
    return {
      testC: false,
    };
  },
  components: {
    CategorySelector,
    CxkEditor,
  },
  methods: {
    // https://developer.mozilla.org/ko/docs/Web/API/Document/execCommand
    clickh1Tag(event) {
      document.execCommand("formatBlock", false, event.target.dataset.command);
    },
    clickBold() {
      document.execCommand("bold");
    },
    clickItalic() {
      document.execCommand("italic");
    },
    sendInput() {
      const inputText = document.querySelector("#editor");
      console.log(inputText);
    },
    changeFontSize(event) {
      console.log(event.target.value);
      document.execCommand("fontSize", false, event.target.value);
    },
  },
  mounted() {},
};
</script>

<style>
font[size="4"] {
  font-size: 10px;
}

font[size="5"] {
  font-size: 12px;
}

font[size="6"] {
  font-size: 20px;
}

font[size="7"] {
  font-size: 100px;
}
</style>
