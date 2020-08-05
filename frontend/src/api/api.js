export default {
  URL: 'http://i3a104.p.ssafy.io/',
  IMAGE_URL: 'http://i3a104.p.ssafy.io/img/',
  ROUTES: {
    // Accounts ROUTES
    signup: '/user/signup/',
    login: '/user/login/',
    checkEmail: '/user/email/',
    checkNickname: '/user/nickname/',
    myPage: '/user/mypage/', // 회원 정보 리턴 페이지..?
    update: '/user/modify/', // 회원 정보 수정
    withdrawal : '/user/withdrawal/', // 회원 탈퇴

    // Recipes ROUTES
    recipeList: '/recipe/all/', // 모든 레시피 리스트를 받기 위한 주소(뒤에 페이지 주소 0부터 시작 / 6개씩 전달)
    searchRecipe: '/recipe/search/', // 레시피 검색을 위한 주소입니다. (선택된 카테고리의 id와 재료의 id를 Array로 보냅니다.)

    recipeDetail: '/recipe/view/', // 레시피 상세 정보를 받기 위한 주소(뒤에 recipe id 붙여주기)

    goRegister: '/recipe/goRegister/', // categories와 ingredients 리스트를 받기 위해 요청을 보내는 주소
    recipeRegister: '/recipe/register/', // 레시피 작성을 위해 데이터를 보내야 되는 곳

    recipeDelte: '/recipe/delete/', // 뒤에 recipe_id 붙여주기

    recipeLike: '/recipe/like/',
    recipeDislike: '/recipe/likeCancel/',


    recipeReview: '/review/view/', // 레시피 아이디를 보내면 레시피의 리뷰들이 반환되는 것같아요.
    reviewRegister: '/review/', // 리뷰 작성을 위한 데이터를 전달할 주소
    reviewDelete: '/review/', // 뒤에 review_id 붙여주기

    // Versus ROUTES
    versusRegister: '/versus/register/', // POST / 요리대전 생성
    versusList: '/versus/all/', // GET / 요리대전 목록 (6개씩)
    versusDetail: '/versus/view/', // GET / 요리대전 상세보기
  }
}