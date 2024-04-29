<script setup>
    import { ref } from 'vue';
    import axios from 'axios'

    const apiPath = "http://localhost:8080/api/habits";
    
    const { habit } = defineProps(['habit']);
    const emit = defineEmits(['closeTimer'])

    async function addDone(){
        try {
            await axios.get(`${apiPath}/addDone?habitId=${habit.id}`);
        } catch (error) {
            console.error(error);
        }
    }

    const resultOfCheck = ref(null);
    async function isHabitDoneCheckAndSaveOrDeleteById(){
        try {
            resultOfCheck.value = (await axios.get(`${apiPath}/isHabitDoneCheckAndSaveOrDeleteById?habitId=${habit.id}`)).data;
        } catch (error) {
            console.error(error);
        }

        // console.log(resultOfCheck.value);
        if(resultOfCheck.value === "You've already done all reps for this period"){
            alert(resultOfCheck.value);
        }
    }
    

    // timer
    const durationTimeTo = ref(new Date().getTime() + habit.durationTime * 60000);
    const minutes = ref(0);
    const seconds = ref(0);
    const timerDisplay = ref('');
    const durationTo = ref(0);

    addDone(habit.id);

    const timerFunc = setInterval(() =>{
        const currDate = new Date().getTime();
        durationTo.value = durationTimeTo.value - currDate;

        seconds.value = parseInt(durationTo.value / 1000);
        minutes.value = parseInt(seconds.value / 60);

        timerDisplay.value = 
        `${minutes.value % 60 < 10 ? "0" + minutes.value % 60 : minutes.value % 60} : ${seconds.value % 60 < 10 ? "0" + seconds.value % 60 : seconds.value % 60}`;

        if(durationTo.value <= 0){
            clearInterval(timerFunc);
            console.log("duration time cleared")
        }

    }, 1000);
    //

    const closeTimerEmit = () => {
        clearInterval(timerFunc);
        isHabitDoneCheckAndSaveOrDeleteById(habit.id);
        emit('closeTimer');
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
        <div class="timer-panel">
            <header>
                <div class="habit-name">
                    {{ habit.habitName }}
                </div>
            </header>
            <div class="timer-container">
                <div class="timer">
                    {{ timerDisplay }}
                </div>
                <div class="end-button">
                    <button @click="closeTimerEmit">
                        Finish rep
                        <div class="warning">If you finish rep before time ends, it won't be saved.</div>
                    </button>
                </div>
            </div>

        </div>
    </div>


</template>

<style scoped> 

    .overlay{
        position: absolute;
        min-width: 900px;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.6);
        z-index: 15;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .timer-panel{
        width: 500px;
        background-color: #181818;
        border-radius: 10px;
        position: relative;
        display: flex;
        flex-direction: column;
    }

    header{
        background-color: v-bind(theme);
        border-top-left-radius: 10px;
        border-top-right-radius: 10px;
        padding: 20px;
        font-size: 20px;
        color: #181818;
    }

    .habit-name{
        text-align: center;
        font-weight: 500;
    }

    .timer-container div{
        display: flex;
    }

    .timer-container{
        display: flex;
        padding: 20px;
        flex-direction: column;
        align-items: center;
    }

    .timer{
        background-color: #222222;
        margin-bottom: 20px;
        height: 100px;
        width: 50%;
        border-radius: 25px;
        font-size: 60px;
        justify-content: center;
        align-items: center;
    }

    .end-button{
        width: 100%;
    }

    .end-button button{
        font-size: 16px;
        background-color: #b84242;
        border-radius: 5px;
        width: 100%;
        height: 100%;
        appearance: none;
        padding: 12px;
        font-size: 20px;
        cursor: pointer;
        transition: all 200ms;
    }

    .end-button button:hover{
        background-color: #963737;
        transform: scale(1.05);
        transition: all 200ms;
    }

    .end-button button .warning {
        visibility: hidden;
        width: 220px;
        background-color: #292929;
        color: #c9c9c9;
        text-align: center;
        border-radius: 10px;
        padding: 5px 0;
        font-size: 15px;
        
        
        /* Position the tooltip */
        position: absolute;
        z-index: 1;
        top: 100%;
        left: 50%;
        margin-left: -110px;
    }

    .end-button button:hover .warning {
        visibility: visible;
    }


</style>