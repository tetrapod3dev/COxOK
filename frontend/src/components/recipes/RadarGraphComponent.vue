<script>
import { Radar } from "vue-chartjs";

export default {
  extends: Radar,
  data() {
    return {
      datacollection: {
        labels: [
          "탄수화물", "지방", "당류", "나트륨", "단백질"
          // "탄수화물", "지방", "단백질"
        ],
        datasets: [
          {
            label: "Nutrient Data Set",
            backgroundColor: "rgba(54, 162, 235, 0.2)",
            borderColor: "rgba(54, 162, 235, 1)",
            pointBackgroundColor: "rgba(54, 162, 235, 1)",
            pointBorderColor: "#fff",
            pointHoverBackgroundColor: "#fff",
            pointHoverBorderColor: "rgba(54, 162, 235, 1)",
            data: [1, 2],
          }]},
      options: {
        legend: {
            display: false
        },
        "tooltips": {
            "callbacks": {
                "title": (tooltipItem, data) => data.labels[tooltipItem[0].index]
            }
        },
        scale: {
          angleLines: {
            display: false
          },
          ticks: {
            suggestedMin: 0,
            suggestedMax: 100,
            stepSize: 20
          }}}};},
  props: {
    recipeDataSet: Object,
  },
  computed: {
    dataTest() {
      return (this.recipeDataSet.calorie !== undefined) ? [
        this.recipeDataSet.carbon,
        this.recipeDataSet.fat,
        this.recipeDataSet.sugar,
        this.recipeDataSet.natrium / 1000,
        this.recipeDataSet.protein
      ] : null
    }
  },
  watch: {
    dataTest() {
      const self = this
      self.datacollection.datasets[0].data = self.dataTest
      self.options.scale.ticks.stepSize = Math.round((Math.max.apply(null, self.dataTest) - Math.min.apply(null, self.dataTest)) / 30) * 10
      self.options.scale.ticks.suggestedMax = (parseInt(Math.max.apply(null, self.dataTest) / 10) + 1) * 10
      
      this.renderChart(self.datacollection, self.options);
    }}
  };
</script>