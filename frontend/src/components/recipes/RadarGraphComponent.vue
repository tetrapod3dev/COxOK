<script>
import { Radar } from "vue-chartjs";

export default {
  extends: Radar,
  data() {
    return {
      datacollection: {
        labels: [
          "칼로리", "탄수화물", "지방", "나트륨", "단백질"
        ],
        datasets: [
          {
            label: "Nutrient Data Set",
            backgroundColor: "rgba(179,181,198,0.2)",
            borderColor: "rgba(179,181,198,1)",
            pointBackgroundColor: "rgba(179,181,198,1)",
            pointBorderColor: "#fff",
            pointHoverBackgroundColor: "#fff",
            pointHoverBorderColor: "rgba(179,181,198,1)",
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
        parseInt(this.recipeDataSet.calorie / 2000 * 100),
        parseInt(this.recipeDataSet.carbon / 130 * 100),
        parseInt(this.recipeDataSet.fat / 51 * 100),
        parseInt(this.recipeDataSet.natrium / 2000 * 100),
        parseInt(this.recipeDataSet.protein / 56 * 100)
      ] : null
    }
  },
  watch: {
    dataTest() {
      const self = this
      self.datacollection.datasets[0].data = self.dataTest
      this.renderChart(self.datacollection, self.options);
    }}
  };
</script>