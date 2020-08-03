<template>
  <div class="container">
    <h4 class="text-center">블로그 게시글 작성 페이지</h4>
    <div class="row justify-content-md-center" id="row_style">
      <div class="col-md-4 col-md-offset-4">
        <div class="form-group">
          <input type="text" class="form-control" placeholder="제목" />
        </div>
        <textarea class="form-control" id="editor" rows="10">임시 텍스트 작성 구역</textarea>
        <br />
        <div class="form-group">
          <button class="btn btn-primary" id="submit">등록</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "BlogPostMakeView",
};
</script>

<style>
</style>