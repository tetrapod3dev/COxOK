<template>
  <div class="__cxk__frame container">
    <div class="btn-toolbar __cxk__btn-toolbar row" role="toolbar">
      <select
        class="__cxk__btn __cxk__selectFontFamily"
        name="cxk-selectFontFamily"
        v-model="cxkSelectFontFamily"
        @change="setCxkSelectFontFamily($event)"
      >
        <option
          v-for="elementFontFamily in listFontFamily"
          :key="elementFontFamily"
          v-text="elementFontFamily"
          :value="elementFontFamily"
          :style="'font-family:' + elementFontFamily"
        />
      </select>
      <select
        class="__cxk__btn __cxk__selectFontSize"
        name="cxk-selectFontSize"
        v-model="cxkSelectFontSize"
        @change="setCxkSelectFontSize($event)"
      >
        <option
          v-for="elementFontSize in listFontSize"
          :key="elementFontSize"
          v-text="elementFontSize"
          :value="elementFontSize"
        />
      </select>
      <span class="__cxk__btn" @click="clickFontColor()">
        <i class="fa fa-pencil"></i>
      </span>
      <input
        id="__cxk__fontcolor"
        type="color"
        v-model="cxkInputFontColor"
        @change="doFontColor($event)"
        hidden
      />
      <span class="__cxk__btn" @click="clickHiliteColor()">
        <i class="fa fa-paint-brush"></i>
      </span>
      <input
        id="__cxk__hiliteColor"
        type="color"
        v-model="cxkInputHiliteColor"
        @change="doHiliteColor($event)"
        hidden
      />

      <span class="__cxk__btn" @click="clickImage()">
        <i class="fa fa-image"></i>
      </span>
      <input
        type="file"
        class="item-file-image"
        id="inputFileToLoad"
        ref="inputFileToLoad"
        @change="loadImageFileAsURL"
        multiple
        hidden
      />
      <span
        v-for="command in commandRelation"
        :key="command.cmd"
        class="__cxk__btn"
        :title="command.desc"
        onmousedown="event.preventDefault();"
        @click="doCommand(command)"
        v-show="!command.noIcon"
      >
        <i v-if="command.icon" :class="'fa fa-' + command.icon"></i>
        {{ showOnlyIcon(command) }}
      </span>
    </div>
    <div class="row">
      <div
        id="__cxk__content"
        class="__cxk__content col-12 ml-auto mr-auto"
        contenteditable="true"
        @input="update"
        @focus="focus"
        @blur="blur"
        v-html="valueText"
      ></div>
    </div>
  </div>
</template>

<script>
import CONFIG from "./cxkeditor.config.js";
import SERVER from "@/api/api";
import axios from "axios";

export default {
  data() {
    return {
      commandRelation: {},
      listFontSize: [],
      listFontFamily: [],
      cxkSelectFontFamily: "Arial",
      cxkSelectFontSize: "16",
      cxkInputFontColor: "#000000",
      cxkInputHiliteColor: "#FFFFFF",
      focusIn: false,
      valueText: "",
    };
  },
  props: {
    value: {
      type: String,
      default: "",
    },
  },
  computed: {
    localValue: {
      get: function () {
        return this.value;
      },
      set: function (newValue) {
        this.$emit("update:value", newValue);
      },
    },
  },
  created() {
    this.commandRelation = CONFIG.COMMAND_RELATION;
    this.listFontSize = CONFIG.LIST_FONT_SIZE;
    this.listFontFamily = CONFIG.LIST_FONT_FAMILY;
    this.valueText = this.value;
  },
  watch: {
    localValue(newVal) {
      if (!this.focusIn) {
        this.valueText = newVal;
      }
    },
  },
  methods: {
    showOnlyIcon: (cmd) => {
      return typeof cmd.icon === "undefined" ? cmd.text : "";
    },

    doCommand: (cmd) => {
      var val =
        typeof cmd.val !== "undefined"
          ? prompt("Value for " + cmd.cmd + "?", cmd.val)
          : "";
      document.execCommand(cmd.cmd, false, val || "");
    },
    setCxkSelectFontSize: (event) => {
      document.execCommand("fontsize", false, 20);
      var fontElements = document
        .getElementById("__cxk__content")
        .getElementsByTagName("font");
      for (var i = 0, len = fontElements.length; i < len; ++i) {
        if (fontElements[i].size == "7") {
          fontElements[i].removeAttribute("size");
          fontElements[i].style.fontSize = event.target.value + "px";
        }
      }
    },
    setCxkSelectFontFamily: (event) => {
      document.execCommand("fontName", false, event.target.value);
    },
    clickFontColor: () => {
      document.getElementById("__cxk__fontcolor").click();
    },
    doFontColor: (event) => {
      document.execCommand("foreColor", false, event.target.value);
    },
    clickHiliteColor: () => {
      document.getElementById("__cxk__hiliteColor").click();
    },
    doHiliteColor: (event) => {
      document.execCommand("hiliteColor", false, event.target.value);
    },
    clickImage: () => {
      document.getElementById("inputFileToLoad").click();
    },
    doImage: (event) => {
      document.execCommand("insertImage", false, event.target.value);
    },
    loadImageFileAsURL: () => {
      let frm = new FormData();
      document.getElementById("inputFileToLoad").files.forEach(function (file) {
        frm.append("photo", file);
      });

      axios
        .post(SERVER.URL + SERVER.ROUTES.photoRegister, frm)
        .then((res) => {
          var imageLoaded = document.createElement("img");
          imageLoaded.src = SERVER.IMAGE_URL + res.data.photo;
          document.getSelection()["focusNode"].appendChild(imageLoaded);
        })
        .catch((err) => console.log(err));
    },
    update(event) {
      this.localValue = event.target.innerHTML;
    },
    focus() {
      this.focusIn = true;
    },
    blur() {
      this.focusIn = false;
    },
  },
};
</script>

<style scoped>
.__cxk__btn-toolbar > .__cxk__btn {
  cursor: pointer;
  font-size: 12px;
  border-radius: 0;

  position: relative;
  padding: 0.25rem 0.5rem;
  font-size: 0.875rem;
  line-height: 1.5;
  border-width: 2px;
  font-weight: 400;
  margin: 5px 0px;
  background-color: #888888;
  color: #ffffff;
  display: inline-block;
  text-align: center;
  vertical-align: middle;
  transition: color 0.15s ease-in-out, background-color 0.15s ease-in-out,
    border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
  box-sizing: border-box;
}

.__cxk__btn-toolbar > .__cxk__btn:hover {
  text-decoration: underline;
}

.__cxk__btn-toolbar select {
  font-size: 12px;
}

.__cxk__btn-toolbar .btn-group {
  padding: 0;
}
.__cxk__content {
  border: solid 1px #999999;
  border-radius: 15px;
  padding: 20px;
  width: 100%;
  min-width: 320px;
  min-height: 200px;
}

#__cxk__content font[size="7"] {
  font-size: 20px;
}
.__cxk__btn-toolbar > .__cxk__btn > .fa:before {
  font-size: 10px;
}
</style>
