<template>
  <div class="wrapper">
    <router-link :to="{ name: 'ClubOfflineDetailView', params: {club_id: club.meetId} }">
      <figure class="card card--1">
        <img :src="imageSrc(club.thumbnailSrc)">
        <figcaption>
          <span class="info">
            <h3>{{club.title}}</h3>
            <span>날짜: {{simpleDate(club.date)}}</span>
            <span class="ml-3">금액: {{club.price}}원</span>
          </span>
        </figcaption>
      </figure>
    </router-link>

    
  </div>
</template>

<script>
import SERVER from '@/api/api'

export default {
  name: 'OfflineClubListItem',
  props: {
    club: Object
  },
  methods: {
    // Thumbnail 주소
    imageSrc(link) {
      return SERVER.IMAGE_URL + link
    },
    simpleDate(date) {
      return date.slice(5,10);
    }
  }
}
</script>

<style scoped>
figure.card {
  position: relative;
  width:250px;
  height:160px;
  background-image: var(--image-src);
  background-position: 50% 50%;
  background-size: 125%;
  transition: background 400ms ease;
  box-shadow: 0 6.7px 5.3px rgba(0, 0, 0, 0.03), 0 22.3px 17.9px rgba(0, 0, 0, 0.05), 0 100px 80px rgba(0, 0, 0, 0.07);
  overflow: hidden;
}
figure.card:hover figcaption {
  transform: translateY(0px);
}
figure.card--1:hover {
  background-position: 50% 100%;
}
figure.card--1 figcaption {
  bottom: 0;
  transform: translateY(80px);
}
figure.card--1 figcaption {
  width: 100%;
  height: 80px;
  padding: 15px 20px;
}
figure.card figcaption {
  display: flex;
  justify-content: space-between;
  align-items: center;
  position: absolute;
  background: #282828;
  transition: transform 400ms ease;
}
figure.card figcaption .info {
  color: #e2e2e2;
  font-family: "Montserrat";
}
figure.card figcaption .info h3 {
  font-size: 1.2rem;
  letter-spacing: 1px;
  margin-bottom: 2px;
}
figure.card figcaption .info span {
  color: #72cc60;
  font-size: 0.85rem;
}

</style>