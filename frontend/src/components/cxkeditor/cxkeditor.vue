<template>
  <div class="__cxk__frame">
    <div class="row">
      <div class="btn-toolbar __cxk__btn-toolbar" role="toolbar">
        <div class="btn-group btn-group-sm mr-2" role="group">
          <select
            class="btn __cxk__btn __cxk__selectFontFamily"
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
            class="btn __cxk__btn __cxk__selectFontSize"
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
          <span class="btn __cxk__btn" @click="clickFontColor()">
            <i class="fa fa-pencil"></i>
          </span>
          <input
            id="__cxk__fontcolor"
            type="color"
            v-model="cxkInputFontColor"
            @change="doFontColor($event)"
            hidden
          />
          <span class="btn __cxk__btn" @click="clickHiliteColor()">
            <i class="fa fa-paint-brush"></i>
          </span>
          <input
            id="__cxk__hiliteColor"
            type="color"
            v-model="cxkInputHiliteColor"
            @change="doHiliteColor($event)"
            hidden
          />

          <span class="btn __cxk__btn" @click="clickImage()">
            <i class="fa fa-image"></i>
          </span>
          <input
            type="file"
            class="item-file-image"
            id="inputFileToLoad"
            ref="inputFileToLoad"
            @change="loadImageFileAsURL"
            hidden
          />
        </div>
        <div class="btn-group btn-group-sm" role="group">
          <span
            v-for="command in commandRelation"
            :key="command.cmd"
            class="btn __cxk__btn"
            :title="command.desc"
            onmousedown="event.preventDefault();"
            @click="doCommand(command)"
            v-show="!command.noIcon"
          >
            <i v-if="command.icon" :class="'fa fa-' + command.icon"></i>
            {{ showOnlyIcon(command) }}
          </span>
        </div>
      </div>
    </div>
    <div
      id="__cxk__content"
      class="__cxk__content"
      contenteditable="true"
      ref="cxkeditorContent"
      @input="udpateEditorContent"
    ></div>
  </div>
</template>

<script>
import CONFIG from "./cxkeditor.config.js";

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
    };
  },
  created() {
    this.commandRelation = CONFIG.COMMAND_RELATION;
    this.listFontSize = CONFIG.LIST_FONT_SIZE;
    this.listFontFamily = CONFIG.LIST_FONT_FAMILY;
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
      var filesSelected = document.getElementById("inputFileToLoad").files;
      if (filesSelected.length > 0) {
        var fileToLoad = filesSelected[0];

        if (fileToLoad.type.match("image")) {
          var fileReader = new FileReader();
          fileReader.onload = function(fileLoadedEvent) {
            var imageLoaded = document.createElement("img");
            imageLoaded.src = fileLoadedEvent.target.result;
            console.log(document.getSelection());
            document.getSelection()["focusNode"].appendChild(imageLoaded);
          };
          fileReader.readAsDataURL(fileToLoad);
        }
      }
    },
    udpateEditorContent() {
      this.$emit(
        "content",
        document.getElementById("__cxk__content").innerHTML
      );
    },
  },
};
</script>

<style scoped>
.__cxk__btn-toolbar > .__cxk__btn {
  cursor: pointer;
}

.__cxk__btn-toolbar > .__cxk__btn:hover {
  text-decoration: underline;
}

.__cxk__btn-toolbar select {
  font-size: 12px;
}

.__cxk__content {
  border: solid 1px #ccc;
  padding: 20px;
  max-width: 855px;
  min-height: 200px;
}

#__cxk__content font[size="7"] {
  font-size: 20px;
}
</style>
