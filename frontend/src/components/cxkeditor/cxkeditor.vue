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
      <div class="__cxk__group-btn-input">
        <span class="__cxk__btn" @click="clickFontColor()">
          <i class="fa fa-pencil"></i>
        </span>
        <input
          id="__cxk__fontcolor"
          class="__cxk__btn-input"
          type="color"
          v-model="cxkInputFontColor"
          @change="doFontColor($event)"
        />
      </div>
      <div class="__cxk__group-btn-input">
        <span class="__cxk__btn" @click="clickHiliteColor()">
          <i class="fa fa-paint-brush"></i>
        </span>
        <input
          id="__cxk__hiliteColor"
          class="__cxk__btn-input"
          type="color"
          v-model="cxkInputHiliteColor"
          @change="doHiliteColor($event)"
        />
      </div>
      <div class="__cxk__group-btn-input">
        <span class="__cxk__btn" @click="clickImage()">
          <i class="fa fa-image"></i>
        </span>
        <input
          type="file"
          class="__cxk__btn-input"
          id="inputFileToLoad"
          ref="inputFileToLoad"
          @change="loadImageFileAsURL"
          multiple
        />
      </div>
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
        @click.once="addClickImageEvent"
        v-html="valueText"
      ></div>
    </div>
    <b-modal
      ref="__cxk__img-model"
      id="modal-prevent-closing"
      title="이미지 크기"
      @show="resetModal"
      @hidden="resetModal"
      @ok="handleOk"
      size="sm"
    >
      <form ref="form" @submit.stop.prevent="handleSubmit" class="__cxk__img-model">
        <b-form-group
          :state="cxkImgState"
          label-cols-sm="4"
          label-cols-lg="3"
          label-align="center"
          label="넓이"
          label-for="__cxk__width-input"
          invalid-feedback="넓이를 입력하세요"
        >
          <b-form-input
            id="__cxk__width-input"
            v-model="cxkSelectImageStyle.width"
            :state="cxkImgState"
            required
          ></b-form-input>
        </b-form-group>
        <b-form-group
          :state="cxkImgState"
          label-cols-sm="4"
          label-cols-lg="3"
          label-align="center"
          label="높이"
          label-for="__cxk__height-input"
          invalid-feedback="높이를 입력하세요"
        >
          <b-form-input
            id="__cxk__height-input"
            v-model="cxkSelectImageStyle.height"
            :state="cxkImgState"
            required
          ></b-form-input>
        </b-form-group>
      </form>
    </b-modal>
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
      cxkSelectImageElement: null,
      cxkSelectImageStyle: {
        width: 0,
        height: 0,
      },
      name: "",
      cxkImgState: null,
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
  mounted() {},
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
    setCxkSelectFontFamily(event) {
      document.execCommand("fontName", false, event.target.value);
    },
    clickFontColor() {
      this.setCaret();
      document.getElementById("__cxk__fontcolor").click();
    },
    doFontColor(event) {
      document.execCommand("foreColor", false, event.target.value);
    },
    clickHiliteColor() {
      this.setCaret();
      document.getElementById("__cxk__hiliteColor").click();
    },
    doHiliteColor(event) {
      document.execCommand("hiliteColor", false, event.target.value);
    },
    clickImage() {
      this.setCaret();
      document.getElementById("inputFileToLoad").click();
    },
    doImage(event) {
      document.execCommand("insertImage", false, event.target.value);
    },
    loadImageFileAsURL() {
      let frm = new FormData();
      document.getElementById("inputFileToLoad").files.forEach(function (file) {
        frm.append("photo", file);
      });

      axios
        .post(SERVER.URL + SERVER.ROUTES.photoRegister, frm)
        .then((res) => {
          for (let index = 0; index < res.data.photo.length; index++) {
            const imageSrc = SERVER.IMAGE_URL + res.data.photo[index];
            document.execCommand("insertImage", false, imageSrc);
          }
          this.addClickImageEvent();
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
    setCaret() {
      var node = document.getElementById("__cxk__content");
      var caretID = "__cxk__caret-position";
      var caretElement = document.createElement("span");
      caretElement.id = caretID;

      window.getSelection().getRangeAt(0).insertNode(caretElement);

      node.blur();
    },
    addClickImageEvent() {
      let cxkContent = document.getElementById("__cxk__content");
      let imgElements = cxkContent.querySelector("img");

      imgElements.addEventListener("click", (event) => {
        this.showImgModal();
        this.cxkSelectImageElement = event.target;
        this.cxkSelectImageStyle.width = event.target.width;
        this.cxkSelectImageStyle.height = event.target.height;
      });
    },
    showImgModal() {
      this.$refs["__cxk__img-model"].show();
    },
    hideImgModal() {
      this.$refs["__cxk__img-model"].hide();
    },
    checkFormValidity() {
      const valid = this.$refs.form.checkValidity();
      this.cxkImgState = valid;
      return valid;
    },
    resetModal() {
      this.name = "";
      this.cxkImgState = null;
    },
    handleOk(bvModalEvt) {
      // Prevent modal from closing
      bvModalEvt.preventDefault();
      // Trigger submit handler
      this.handleSubmit();
    },
    handleSubmit() {
      // Exit when the form isn't valid
      if (!this.checkFormValidity()) {
        return;
      }
      this.cxkSelectImageElement.style.width =
        this.cxkSelectImageStyle.width + "px";
      this.cxkSelectImageElement.style.height =
        this.cxkSelectImageStyle.height + "px";

      this.localValue = document.getElementById("__cxk__content").innerHTML;
      // Hide the modal manually
      this.$nextTick(() => {
        this.$bvModal.hide("modal-prevent-closing");
      });
    },
  },
};
</script>

<style scoped>
.__cxk__btn-toolbar .__cxk__btn {
  cursor: pointer;
  font-size: 12px;
  border-radius: 0;
  position: relative;
  padding: 0.25rem 0.5rem;
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
.__cxk__group-btn-input {
  position: relative;
  padding: 0;
  margin: 5px 0px;
  width: 30px;
  height: 29px;
}
.__cxk__group-btn-input .__cxk__btn,
.__cxk__group-btn-input .__cxk__btn-input {
  position: absolute;
  height: 100%;
  width: 100%;
  margin: 0;
  top: 0;
  left: 0;
}
.__cxk__btn-input {
  z-index: 1;
  opacity: 0;
  width: 26px;
  height: 29px;
  margin: 5px 0px;
}

.__cxk__btn-toolbar .__cxk__btn:hover {
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

.__cxk__img-model .col-form-label {
  padding: 12px 5px !important;
}
</style>
