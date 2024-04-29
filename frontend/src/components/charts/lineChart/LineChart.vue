<script setup>
    import { Line } from 'vue-chartjs'
    import { 
        Chart as ChartJS, 
        Title, 
        Tooltip, 
        Legend, 
        CategoryScale, 
        LinearScale, 
        PointElement, 
        LineElement, 
        Filler 
    } from 'chart.js'
    import { ref, onMounted } from 'vue'
    import * as lineChartConfig from './lineChartConfig.js'
    
    ChartJS.register(Title, Tooltip, Legend, CategoryScale, 
        LinearScale, PointElement, LineElement, Filler);

    const loaded = ref(false);
    const { habitsArr } = defineProps(['habitsArr']);
    const chartOptions = lineChartConfig.chartOptions;
    const chartData = ref(null);

    onMounted(async () =>{
        loaded.value = false;

        try{
            chartData.value = await lineChartConfig.getChartData(habitsArr);
               
            loaded.value = true;

        } catch(e) {
            console.error(e)
        }
    })

</script>

<template>

    <!-- <div>{{ this.loaded }}</div> -->

    <Line v-if="loaded"
      :options="chartOptions"
      :data="chartData"
    />

</template>

<style>



</style>