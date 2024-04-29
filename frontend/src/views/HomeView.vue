<script setup>
    import { ref } from 'vue'
    import axios from 'axios'

    import Habit from "../components/habits/Habit.vue";
    import Overlay from "../components/habits/AddOverlay.vue";

    const apiPath = "http://localhost:8080/api/habits";


    const findHabits = ref([]);
    const disabledAside = ref(false);
    
    async function findAll(){
        try {
            findHabits.value = (await axios.get(`${apiPath}/all`)).data;
        } catch (error) {
            console.error(error);
        }
    }

    async function findByStatus(status){
        try {
            findHabits.value = (await axios.get(`${apiPath}/findAllByStatus/${status}`)).data;
            // console.log(findHabits.value);
        } catch (error) {
            console.error(error);
        }
    }

    findAll()
    
    
    
    const showOverlay = ref(false);

    const onCloseOverlay = () => {
        showOverlay.value = false
        findAll()
    }

    const onCloseHabitPanel = () =>{
        disabledAside.value = false;
        setTimeout(() =>{findAll()}, 200)
        
    }
    
    
    
    const onOpenHabitPanel = () => {
        disabledAside.value = true;
    }


</script>

<template>

    <Overlay v-if="showOverlay"
        @showOverlay="onCloseOverlay"
        />

    <div class="home-view-container">
        
        <div class="habits-container">
            <Habit 
            v-for="habit in findHabits"
            :key="habit.id" 
            :habit="habit" 
            @closeHabitPanel="onCloseHabitPanel"
            @openHabitPanelEmit="onOpenHabitPanel"
            />
        </div>
        <div class="aside-panel">
            <div class="disabled" v-if="disabledAside"></div>
            <div class="navi">
                <div class="navi-item">
                    <button class="navi-button" 
                    @click="findAll">
                        All Habits
                    </button>
                </div>
                <div class="navi-item">
                    <button class="navi-button" 
                    @click="findByStatus('InProgress')">
                        Habits in-progress
                    </button>
                </div>
                <div class="navi-item">
                    <button class="navi-button"
                    @click="findByStatus('Done')">
                        Done Habits
                    </button>
                </div>
            </div>
            <div class="buttons">
                <button @click="showOverlay=true">Add new habit</button>
            </div>
        </div>

    </div>

</template>

<style scoped>

    .home-view-container{
        display: flex;
        height: 85%;
    }


    .habits-container{
        height: 100%;
        width: 75%;
        display: flex;
        align-items: center;
        flex-wrap: wrap;
        align-content: flex-start;
        top: 0;
        left: 0;
        
    }

    .aside-panel{
        height: 100%;
        display: flex;
        flex-direction: column;
        width: 25%;
        background-color: #181818;/* 35495e */
        border-left: 3px solid #42b883;
        border-right: 3px solid #42b883;
        align-items: center;
        justify-content: flex-start;
    }
    
    .disabled{
        position: absolute;
        z-index: 2;
        width: 100%;
        height: 100%;
    }
    
    .navi{
        height: 150px;
        width: 100%;
        display: flex;
        flex-direction: column;
    }

    .navi-item{
        width: 100%;
    }

    .navi-item button{
        color: #42b883;
        background-color: #181818;
        width: 100%;
        margin: 0px auto 0;
        appearance: none;
        padding: 12px;
        font-size: 20px;
        font-weight: 600;
        cursor: pointer;
        transition: all 200ms;
        border: none;
        border-bottom: 3px solid #42b883;
    }

    .navi-item button:hover{
        background-color: #42b883;
        color: #181818;
        transform: none;
    }


    .aside-panel .buttons{
        width: 80%;
        margin-top: 0px;
    }

    .buttons button{
        background-color: #42b883;
        border-radius: 5px;
        clear: both;
        width: 100%;
        margin-top: 20px;
        appearance: none;
        padding: 12px;
        font-size: 20px;
        font-weight: 600;
        cursor: pointer;
        transition: all 200ms;
    }

    button:hover{
        background-color: #37996d;
        transform: scale(1.05);
        transition: all 200ms;
    }


    

</style>