<script setup>
    import axios from 'axios'
    import { ref } from 'vue';
    
    import ChangeSettingsOverlay from './SettingsOverlay.vue';
    import DoRepOfHabit from './DoRepOfHabit.vue';

    const apiPath = "http://localhost:8080/api/habits";
    const {habit, icon} = defineProps(['habit', 'icon']);
    
    const emit = defineEmits(['closeHabitPanel']);


    const emitCloseHabitPanel = () =>{
        emit('closeHabitPanel');
        
    }


    async function deleteById(){
        try {
            await axios.get(`${apiPath}/deleteById?id=${habit.id}`);
        } catch (error) {
            console.error(error);
        }
        emitCloseHabitPanel()
    }

    const showSettings = ref(false);
    const onCloseSettingsOverlay = () => {
        showSettings.value = false;
        emitCloseHabitPanel()
    }

    const showTimer = ref(false)
    const openTimer = () => {
        showTimer.value = true;
    }

    const onCloseTimer = () => {
        showTimer.value = false;
        emitCloseHabitPanel()
    }

    let canDoRepOfHabit = false;
    if(!habit.active){
        canDoRepOfHabit = true;
    } else{
        canDoRepOfHabit = false;
    }

    const theme = ref(habit.color);

    switch(habit.color){
        case "BLUE":
        theme.value = '#5a8eff';
            break;
        case "GREEN":
        theme.value = '#42b883';
            break;
        case "PURPLE":
        theme.value = '#a985d6';
            break;
        case "PINK":
        theme.value = '#ffa6e6';
            break;
        case "YELLOW":
        theme.value = '#fff8a6';
            break;
    }

</script>

<template>

    

    <div class="overlay">


        <ChangeSettingsOverlay v-if="showSettings"
        @closeSettingsOverlay="onCloseSettingsOverlay"
        :habit="habit"/>

        <DoRepOfHabit v-if="showTimer"
        :habit="habit"
        @closeTimer="onCloseTimer"/>


        <div class="habit-panel">
            <header>
                <div class="icon-container header-container">
                    <div class="icon">{{ icon }}</div>
                </div>
                <div class="habit-name-container header-container">
                    {{ habit.habitName }}
                </div>
                <div class="close-button">
                    <button @click="emitCloseHabitPanel"><p>+</p></button>
                </div>
            </header>
            <div class="habit-data-container">
                <div class="description half-panel">
                    {{ habit.description }}
                </div>
                <div class="half-panel">
                    <div class="other-data">
                        <div class="info-row">
                            <div class="half-row">
                                {{ `${habit.habitDoneList.length > 0 ? habit.habitDoneList[habit.habitDoneList.length - 1].numRepsDone : "0"}/${habit.numReps}\n${habit.period}` }}
                            </div>
                            <div class="half-row">
                                {{ habit.created }}
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="half-row">
                                <input type="checkbox"
                                :checked="habit.active"
                                :id="`active${habit.id}`"
                                disabled
                                >
                                <label :for="`active${habit.id}`">
                                    Active
                                </label>
                            </div>
                            <div class="half-row">
                                <input type="checkbox"
                                :checked="habit.remind"
                                :id="`remind${habit.id}`"
                                disabled
                                >
                                <label :for="`remind${habit.id}`">
                                    Remind
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="buttons-container">
                        <div class="button-item">
                            <button @click="openTimer" :disabled="canDoRepOfHabit">Do rep of habit</button>
                        </div>
                        <div class="button-item">
                            <button @click="showSettings=true">Change settings</button>
                        </div>
                        <div class="button-item">
                            <button @click="deleteById">Delete this habit</button>
                        </div>
                        

                    </div>
                </div>
                
            </div>

        </div>
    </div>



</template>

<style scoped>

    .overlay{
        position: absolute;
        width: 100%;
        height: 100%;
        z-index: 15;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .habit-panel{
        width: 700px;
        background-color: #181818;
        border: 3px solid v-bind(theme);
        border-radius: 35px;
        position: relative;
        display: flex;
        flex-direction: column;
        box-shadow: 0 0 25px 20px rgb(12, 12, 12, 0.8);
    }

    .habit-panel div,
    header{
        display: flex;
    }


    header{
        padding-top: 20px;
        margin-top: 0;
        flex-wrap: wrap;
        background-color: v-bind(theme);
        font-size: 30px;
        justify-content: space-evenly;
        border-top-left-radius: 25px;
        border-top-right-radius: 25px;
        padding-bottom: 20px;
    }

    .header-container{
        width: 40%;
        justify-content: flex-start;
        align-items: center;
        color: #181818;
        font-weight: bold;
    }

    .icon{
        justify-content: center;
        align-items: center;
        width: 70px;
        height: 70px;
        background-color: #181818;
        border-radius: 35px;
        color: black;
        font-size: 40px;
    }

    .close-button{
        position: absolute;
        top: 20px;
        right: 20px; 
        z-index: 3;
    }

    .close-button button{
        appearance: none;
        width: 30px;
        height: 30px;
        font-size: 25px;
        color: v-bind(theme);
        background-color: #181818;
        border-radius: 15px;
        cursor: pointer;
        
    }

    .close-button button p {
        position: relative;
        bottom: 4px;
        transform: rotate(45deg);
    }

    button{
        transition: all 200ms;
    }

    button:hover{
        background-color: #222222;
        transform: scale(1.05);
        transition: all 200ms;
    }

    

    .habit-data-container{
        padding: 20px;
        height: 340px;
    }

    .half-panel{
        height: 100%;
        width: 50%;
        flex-wrap: wrap;
        padding-left: 20px;
    }

    .description{
        height: 100%;
        border-radius: 20px;
        background-color: #222222;
        padding: 10px;
    }

    .other-data{
        width: 100%;
        flex-direction: column;
        height: 40%;
    }

    .info-row{
        
        height: 50%;
        width: 100%;
    }

    .half-row{
        height: 100%;
        width: 50%;
        justify-content: center;
        align-items: center;
    }

    .buttons-container{
        flex-direction: column;
        height: 60%;
        width: 100%;
    }

    .button-item{
        width: 100%;
        height: 60px;
        padding: 5px 0;   
    }

    .button-item button{
        font-size: 16px;
        background-color: #42b883;
        border-radius: 5px;
        width: 100%;
        height: 100%;
        appearance: none;
        padding: 12px;
        font-size: 20px;
        cursor: pointer;
    }


    .button-item:last-child button{
        background-color: #b84242;
    }
    
    .button-item button:disabled{
        background-color: #5b7c6d;
    }
    .button-item button:disabled:hover{
        cursor: unset;
        transform: none;
    }


</style>
