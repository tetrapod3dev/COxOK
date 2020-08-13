<template>
  <div class="search-form">
    <div class="row px-5">
      <div class="col-6 row">
        <div v-for="(categoryGroup, index) in categoryGroups" :key="index" class="col-4">
          <div
            v-for="category in categoryGroup"
            :key="category['category_id']"
            class="d-flex justify-content-start my-2"
          >
            <input
              type="checkbox"
              v-model="checker[category.category_id]"
              class="mr-2 mt-1"
              @click="selectCategory(category.category_id)"
            />
            {{ category['category_name']}}
          </div>
        </div>
      </div>

      <div class="col-6">
        <div class="row">
          <div class="col-7 align-self-center">
            <b-form-input
              list="input-list"
              id="input-with-list"
              v-model="textInput"
              @keydown.enter="addIngredient"
            ></b-form-input>
          </div>
          <b-form-datalist id="input-list" :options="ingredientsName"></b-form-datalist>
          <div @click="addIngredient">
            <n-button type="primary" round block>재료 추가</n-button>
          </div>
        </div>
        <div class="row">
          <div class="px-5">
            <button
              v-for="(ingredient, index) in selectedIngredientsName"
              :key="index"
              class="btn btn-outline-secondary mt-3 mx-3"
            >
              {{ ingredient }}
              <span @click="removeIngredient(index)">X</span>
            </button>
          </div>
        </div>
      </div>
      <div class="row col-12 mt-5 mb-5">
        <div class="col-4">
                <div class="tick-slider mb-3">
                    <div class="tick-slider-header">
                        <h5><label for="weightSlider">난이도</label></h5>
                        <h5>level</h5>
                    </div>
                    <div class="tick-slider-value-container">
                        <div id="weightLabelMin" class="tick-slider-label">1</div>
                        <div id="weightLabelMax" class="tick-slider-label">5</div>
                        <div id="weightValue" class="tick-slider-value"></div>
                    </div>
                    <div class="tick-slider-background"></div>
                    <div id="weightProgress" class="tick-slider-progress"></div>
                    <div id="weightTicks" class="tick-slider-tick-container"></div>
                    <input
                        id="weightSlider"
                        class="tick-slider-input"
                        type="range"
                        min="1"
                        max="5"
                        step="1"
                        value="3"
                        v-model="level"
                        data-tick-step="5"
                        data-tick-id="weightTicks"
                        data-value-id="weightValue"
                        data-progress-id="weightProgress"
                        data-handle-size="18"
                        data-min-label-id="weightLabelMin"
                        data-max-label-id="weightLabelMax"
                    />
         
                <div class="tick-slider">
            <div class="tick-slider-header">
                <h5><label for="sizeSlider">소요 시간</label></h5>
                <h5>분</h5>
            </div>
            <div class="tick-slider-value-container">
                <div id="sizeLabelMin" class="tick-slider-label">0</div>
                <div id="sizeLabelMax" class="tick-slider-label">120</div>
                <div id="sizeValue" class="tick-slider-value"></div>
            </div>
            <div class="tick-slider-background"></div>
            <div id="sizeProgress" class="tick-slider-progress"></div>
            <div id="sizeTicks" class="tick-slider-tick-container"></div>
            <input
                id="sizeSlider"
                class="tick-slider-input"
                type="range"
                min="0"
                max="120"
                step="5"
                value="0"
                v-model="cookTime"
                data-tick-step="5"
                data-tick-id="sizeTicks"
                data-value-id="sizeValue"
                data-progress-id="sizeProgress"
                data-handle-size="18"
                data-min-label-id="sizeLabelMin"
                data-max-label-id="sizeLabelMax"
            />
        </div>
  
        </div>
      </div>
          


      </div>
    </div>

    <button class="btn btn-secondary" @click="searchRecipe">검색</button>

    <button class="btn btn-danger" @click="removeSelect">초기화</button>
  </div>
</template>

<script>
import axios from "axios";
import SERVER from "@/api/api";
import { mapActions, mapGetters } from "vuex";
import { Button } from "@/components/global";

export default {
  name: "CategorySelector",
  data() {
    return {
      isShow: false,
      toggle: null,
      categories: [],
      ingredients: {},
      ingredientsName: [],
      selectedCategory: [],
      selectedIngredientsName: [],
      selectedIngredients: [],
      textInput: null,
    };
  },
  components: {
    [Button.name]: Button,
  },
  computed: {
    categoryGroups() {
      return [
        this.categories.slice(0, 8),
        this.categories.slice(8, 15),
        this.categories.slice(15),
      ];
    },
    searchData() {
      return {
        selectedCategory: this.selectedCategory,
        selectedIngredients: this.selectedIngredients,
        selectedIngredientsName: this.selectedIngredientsName,
      };
    },
    ...mapGetters(["searchingData"]),
    checker() {
      let tempChecker = {};
      const self = this;

      this.categories.forEach(function (category) {
        if (self.selectedCategory.indexOf(category.category_id) >= 0) {
          tempChecker[category.category_id] = true;
        } else {
          tempChecker[category.category_id] = false;
        }
      });

      return tempChecker;
    },
  },
  methods: {
    ...mapActions(["changeSearchData"]),
    changeShow() {
      this.isShow = !this.isShow;
    },
    ischecked(id) {
      return this.selectedCategory.indexOf(id) >= 0 ? true : false;
    },
    selectCategory(id) {
      if (this.selectedCategory.indexOf(id) >= 0) {
        this.selectedCategory.splice(this.selectedCategory.indexOf(id), 1);
      } else {
        this.selectedCategory.push(id);
      }
    },
    addIngredient() {
      if (this.ingredientsName.indexOf(this.textInput) >= 0) {
        this.selectedIngredients.push(this.ingredients[this.textInput]);
        this.selectedIngredientsName.push(this.textInput);
        this.textInput = "";
      } else {
        alert("재료 목록에 존재하는 재료를 입력해주세요!");
      }
    },
    removeIngredient(index) {
      this.selectedIngredients.splice(index, 1);
      this.selectedIngredientsName.splice(index, 1);
    },
    searchRecipe() {
      this.changeSearchData(this.searchData);
      this.$emit("searchRecipe");
    },
    removeSelect() {
      this.changeSearchData({
        selectedCategory: [],
        selectedIngredients: [],
        selectedIngredientsName: [],
      });
      this.selectedCategory = [];
      this.selectedIngredients = [];
      this.selectedIngredientsName = [];
      this.$emit("removeSelect");
    },
  },
  created() {
    axios
      .get(SERVER.URL + SERVER.ROUTES.getCategory)
      .then((res) => {
        const self = this;
        res.data.catogories.forEach(function (category) {
          self.categories.push({
            category_name: category.foodCategoryName,
            category_id: category.foodCategoryId,
          });
        });

        res.data.ingredients.forEach(function (ingredient) {
          self.ingredients[ingredient.name] = ingredient.ingredientId;
        });

        this.ingredientsName = Object.keys(this.ingredients);
      })
      .catch((err) => console.log(err.response));

    this.selectedCategory = this.searchingData.selectedCategory;
    this.selectedIngredients = this.searchingData.selectedIngredients;
    this.selectedIngredientsName = this.searchingData.selectedIngredientsName;
  },

  // 난이도, 소요시간
  init() {
    const sliders = document.getElementsByClassName("tick-slider-input");

    for (let slider of sliders) {
        slider.oninput = this.onSliderInput;

        this.updateValue(slider);
        this.updateValuePosition(slider);
        this.updateLabels(slider);
        this.updateProgress(slider);

        this.setTicks(slider);
    }
    },

    onSliderInput(event) {
        this.updateValue(event.target);
        this.updateValuePosition(event.target);
        this.updateLabels(event.target);
        this.updateProgress(event.target);
    },

    updateValue(slider) {
        let value = document.getElementById(slider.dataset.valueId);

    value.innerHTML = "<div>" + slider.value + "</div>";
    },

    updateValuePosition(slider) {
        let value = document.getElementById(slider.dataset.valueId);

    const percent = this.getSliderPercent(slider);
    const sliderWidth = slider.getBoundingClientRect().width;
    const valueWidth = value.getBoundingClientRect().width;
    const handleSize = slider.dataset.handleSize;

    let left = percent * (sliderWidth - handleSize) + handleSize / 2 - valueWidth / 2;

    left = Math.min(left, sliderWidth - valueWidth);
    left = slider.value === slider.min ? 0 : left;

    value.style.left = left + "px";
    },

    updateLabels(slider) {
        const value = document.getElementById(slider.dataset.valueId);
        const minLabel = document.getElementById(slider.dataset.minLabelId);
        const maxLabel = document.getElementById(slider.dataset.maxLabelId);

    const valueRect = value.getBoundingClientRect();
    const minLabelRect = minLabel.getBoundingClientRect();
    const maxLabelRect = maxLabel.getBoundingClientRect();

    const minLabelDelta = valueRect.left - (minLabelRect.left);
    const maxLabelDelta = maxLabelRect.left - valueRect.left;

    const deltaThreshold = 32;

    if (minLabelDelta < deltaThreshold) minLabel.classList.add("hidden");
    else minLabel.classList.remove("hidden");

    if (maxLabelDelta < deltaThreshold) maxLabel.classList.add("hidden");
    else maxLabel.classList.remove("hidden");
    },

    updateProgress(slider) {
        let progress = document.getElementById(slider.dataset.progressId);
        const percent = this.getSliderPercent(slider);

    progress.style.width = percent * 100 + "%";
    },

    getSliderPercent(slider) {
        const range = slider.max - slider.min;
        const absValue = slider.value - slider.min;

    return absValue / range;
    },

    setTicks(slider) {
        let container = document.getElementById(slider.dataset.tickId);
        const spacing = parseFloat(slider.dataset.tickStep);
        const sliderRange = slider.max - slider.min;
        const tickCount = sliderRange / spacing + 1; // +1 to account for 0

    for (let ii = 0; ii < tickCount; ii++) {
        let tick = document.createElement("span");

        tick.className = "tick-slider-tick";

        container.appendChild(tick);
    }
    },

    onResize() {
        const sliders = document.getElementsByClassName("tick-slider-input");

    for (let slider of sliders) {
        this.updateValuePosition(slider);
    }
  }
};
</script>

<style>
/* 난이도, 소요시간 */
.wrapper {
  --yellow: #ffd049;
  --light-yellow: #fdf2d2;
  --orange: #ffa929;
  --light-gray: #e3e4e8;
  --gray: #71738b;
  --light-blue: #7a7c93;
  --blue: #34385a;

  --slider-handle-size: 14px;
  --slider-handle-border-radius: 2px;
  --slider-handle-margin-top: -4px;
  --slider-track-height: 6px;
  --slider-track-border-radius: 4px;
}

#slider {
  position: absolute;
  width: 100%;
  height: 100%;

  display: flex;
  justify-content: center;
  align-items: center;
}

.search-form {
  padding: 56px 40px;
  border-radius: 40px;
  box-shadow: 0px 8px 40px rgba(128, 128, 128, 0.15);
}

.tick-slider-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 24px;
}

.tick-slider-header > h5 {
  margin: 0;

  font-family: "Poppins", sans-serif;
  font-size: 18px;
  font-weight: 300;
  color: var(--gray);
}

.tick-slider {
  position: relative;

  width: 100%;
}

.tick-slider-value-container {
  position: relative;
  width: 100%;

  display: flex;
  justify-content: space-between;
  align-items: center;

  margin-bottom: 12px;

  font-family: "Hind Madurai", sans-serif;
  font-size: 18px;
  color: var(--gray);
}

.tick-slider-value {
  position: absolute;
  top: 0;

  font-weight: bold;

  color: var(--blue);

  border-radius: var(--slider-handle-border-radius);
}

.tick-slider-value > div {
  animation: bulge 0.3s ease-out;
}

.tick-slider-background,
.tick-slider-progress,
.tick-slider-tick-container {
  position: absolute;
  bottom: 5px;
  left: 0;

  height: var(--slider-track-height);

  pointer-events: none;

  border-radius: var(--slider-track-border-radius);

  z-index: 0;
}

.tick-slider-background {
  width: 100%;
  background-color: var(--light-gray);
}

.tick-slider-progress {
  background-color: var(--yellow);
}

.tick-slider-tick-container {
  width: 100%;

  display: flex;
  justify-content: space-between;
  align-items: center;

  padding: 0 calc(var(--slider-handle-size) / 2);
}

.tick-slider-tick {
  width: 2px;
  height: 2px;

  border-radius: 50%;

  background-color: white;
}

.tick-slider-label {
  opacity: 0.85;
  transition: opacity 0.1s ease;
}

.tick-slider-label.hidden {
  opacity: 0;
}

@keyframes bulge {
  0% {
    transform: scale(1);
  }

  25% {
    transform: scale(1.1);
  }

  100% {
    transform: scale(1);
  }
}

/*

REMOVE SLIDER STYLE DEFAULTS
*/
input[type="range"] {
  -webkit-appearance: none;

  width: 100%;
  height: 100%;

  background: transparent;
  outline: none;

  margin: 5px 0;
}

input[type="range"]::-webkit-slider-thumb {
  -webkit-appearance: none;

  border: none;
}

input[type="range"]:focus {
  outline: none;
}

input[type="range"]::-moz-focus-outer {
  border: 0;
}

/*

HANDLE
*/
input[type="range"]::-webkit-slider-thumb {
  -webkit-appearance: none;

  width: var(--slider-handle-size);
  height: var(--slider-handle-size);

  background: var(--orange);

  border-radius: var(--slider-handle-border-radius);

  cursor: pointer;

  margin-top: var(--slider-handle-margin-top);

  -webkit-transform: scale(1);
  transform: scale(1);

  transition: transform 0.25s cubic-bezier(0.175, 0.885, 0.32, 1.275);
}

input[type="range"]:hover::-webkit-slider-thumb,
input[type="range"]:focus::-webkit-slider-thumb {
  transform: scale(1.2);
}

input[type="range"]::-moz-range-thumb {
  -webkit-appearance: none;

  width: var(--slider-handle-size);
  height: var(--slider-handle-size);

  background: var(--orange);

  border: none;
  border-radius: var(--slider-handle-border-radius);

  cursor: pointer;

  transition: transform 0.25s cubic-bezier(0.175, 0.885, 0.32, 1.275);
}

input[type="range"]:hover::-moz-range-thumb,
input[type="range"]:focus::-moz-range-thumb {
  transform: scale(1.2);
}

/*

TRACK
*/

input[type="range"]::-webkit-slider-runnable-track {
  width: 100%;
  height: var(--slider-track-height);

  cursor: pointer;

  background: none;

  border-radius: var(--slider-track-border-radius);
}

input[type="range"]::-moz-range-track {
  width: 100%;
  height: var(--slider-track-height);

  cursor: pointer;

  background: none;

  border-radius: var(--slider-track-border-radius);
}

input[type="range"]:focus::-webkit-slider-runnable-track {
  background: none;
}
input[type="range"]:active::-webkit-slider-runnable-track {
  background: none;
}
</style>