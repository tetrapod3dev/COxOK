<template>
  <section>
    <div class="columns">
      <div class="column">
        <line-chart :recipeDataSet="recipeDataSet" />
      </div>
    </div>
  </section>
</template>

<script>
  import LineChart from '@/components/recipes/RadarGraphComponent.vue'

  export default {
    name: 'RadarGraph',
    components: {
      LineChart,
    },
    props: {
      recipeDataSet: Object,
    }
  }
</script>
