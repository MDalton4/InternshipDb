<script>
    import { Bar, mixins } from 'vue-chartjs'

    const { reactiveProp } = mixins

    export default {
        extends: Bar,
        mixins: [reactiveProp],
        name: "BarChart",
        props: ['options', 'chartData'],
        mounted() {
            this.renderChart(this.chartData, this.options);
        },
    }
</script>

<style scoped>

</style>
