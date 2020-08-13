<template>
  <div>
    <div class="row col-12 col-md-8 ml-auto mr-auto" >
      <div class="mt-auto mb-auto ml-auto">
        <b-form-rating size="lg" variant="warning" v-model="reviewData.rating" inline></b-form-rating>
      </div>
      <div class="col-8 mt-auto mb-4">
        <fg-input
          class="no-border form-control-lg"
          id="review-input"
          type="text"
          placeholder="댓글 작성해주세요"
          v-model="reviewData.content"
        ></fg-input>
        </div>
      <div class="mt-auto mb-auto" @click="submitReview" >
        <n-button type="primary" round block>
          <i class="now-ui-icons ui-1_send"></i> 작성
        </n-button>
      </div>
    </div>
  </div>
</template>

<script>
import { Button, FormGroupInput as FgInput } from "@/components/global";

export default {
  name: "ReviewMake",
  data() {
    return {
      reviewData: {
        rating: 1,
        content: null,
      },
    };
  },
  components: {
    FgInput,
    [Button.name]: Button,
  },
  methods: {
    submitReview() {
      this.$emit("submitReview", this.reviewData);
      this.reviewData = {
        rating: 1,
        content: null,
      };
    },
  },
};
</script>

<style>
</style>