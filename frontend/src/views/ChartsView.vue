<script setup>
    import LineChart from '../components/charts/lineChart/LineChart.vue';
    import { ref } from 'vue';
    import axios from 'axios'

    const apiPath = "http://localhost:8080/api/habits";
    const habitsArr = ref();
    const render = ref(false);
    async function findAll(){
        try {
            habitsArr.value = (await axios.get(`${apiPath}/all`)).data;
            render.value = true;
        } catch (error) {
            console.error(error);
        }
    }

    findAll()
    
</script>

<template>

    <div class="charts-container">
        <LineChart v-if="render"
        :habitsArr="habitsArr"/>
    </div>

</template>

<style scoped>

    .charts-container{
        display: flex;
        width: 100%;
        height: 85%;
        align-items: flex-start;
        justify-content: flex-start;
        flex-wrap: wrap;
    }



</style>