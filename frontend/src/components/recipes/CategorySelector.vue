<template>
  <div>
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
    </div>

    <button class="btn btn-outline-primary" @click="searchRecipe">검색</button>

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
      .get(SERVER.URL + SERVER.ROUTES.goRegister)
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
      .catch((err) => console.log(err));

    this.selectedCategory = this.searchingData.selectedCategory;
    this.selectedIngredients = this.searchingData.selectedIngredients;
    this.selectedIngredientsName = this.searchingData.selectedIngredientsName;
  },
};
</script>

<style>
</style>