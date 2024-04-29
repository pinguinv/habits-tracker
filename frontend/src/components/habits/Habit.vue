<script setup>
    import { ref, computed } from 'vue';
    import HabitPanel from './HabitPanel.vue'

    const {habit} = defineProps(['habit']);


    let smallDesc = "";

    if(habit.description != null){
        if(habit.description.length >= 72){
            smallDesc = habit.description.substring(0, 72) + "...";
        } else {
            smallDesc = habit.description;
        }
    }
    

    let icon = "";

    switch(habit.icon){
        case "ICON1":
        icon = "⚽"
            break;
        case "ICON2":
        icon = "🎤"
            break;
        case "ICON3":
        icon = "🎨"
            break;
        case "ICON4":
        icon = "🎸"
            break;
        case "ICON5":
        icon = "📖"
            break;
    }
    
    const showHabitPanel = ref(false);
    const emit = defineEmits(['closeHabitPanel',
                            'openHabitPanelEmit']);
    const onCloseHabitPanel = () => {
        showHabitPanel.value = false;
        emit('closeHabitPanel');
    }

    const openHabitPanel = () => {
        showHabitPanel.value = true;
        emit('openHabitPanelEmit');
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

    <HabitPanel v-if="showHabitPanel" 
    @closeHabitPanel="onCloseHabitPanel"
    :habit="habit" 
    :icon="icon"
    />

    <div class="habit" @click="openHabitPanel">
        <div class="habit-icon-container">
            <div class="icon">
                <p>{{icon}}</p>
            </div>
        </div>
        <div class="habit-name">
            <p>{{ habit.habitName }}</p>
        </div>
        <div class="habit-description">
            <p>{{ smallDesc }}</p>
        </div>
        <div class="habit-reps">
            <p>{{ `${habit.habitDoneList.length > 0 ? habit.habitDoneList[habit.habitDoneList.length - 1].numRepsDone : "0"}/${habit.numReps}\n${habit.period}` }}</p>
        </div>
        <div class="habit-created">
            <p>{{ habit.created }}</p>
        </div>
        <div class="habit-remind" >

            <div>
                <input type="checkbox"
                :checked="habit.active"
                :id="`active${habit.id}`"
                disabled
                >
                <label :for="`active${habit.id}`">
                    Active
                </label>
            </div>
            
            <div>
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

</template>


<style scoped>


    .habit{
        box-sizing: border-box;
        background-color: #181818;
        border: 3px solid v-bind(theme);
        width: 880px;
        height: 100px;
        border-radius: 35px;
        margin-top: 20px;
        display: flex;
        cursor: pointer;
        transition: all 200ms;
    }
    
    .habit:hover{
        background-color: #222222;
        transform: scale(1.03);
        transition: all 200ms;
    }

    .habit p {
        font-size: 15px;
        max-width: 100%;
        overflow: hidden;
        overflow-wrap: break-word;
        text-align: center;
    }

    .habit div {
        overflow: hidden;
        padding: 10px;
        height: 100%;
        align-items: center;
        display: flex;
    }

    .habit-icon-container{
        background-color: v-bind(theme);
        width: 100px;
        border-top-left-radius: 25px;
        border-bottom-left-radius: 25px;
        
    }

    .habit-icon-container .icon{
        width: 70px;
        height: 70px;
        background-color: #181818;
        border-radius: 35px;
    }

    .habit-icon-container .icon p{
        font-size: 40px;
        color: white;
    }

    .habit-name{
        width: 130px;
        padding: 10px;
    }

    .habit-name > p{
        font-size: 18px;
        font-weight: bold;
    }

    .habit-description{
        width: 350px;
        
    }

    .habit-reps{
        width: 80px;
    }

    .habit-created{
        width: 100px;
    }

    .habit-remind{
        display: flex;
        flex-wrap: wrap;
        width: 120px;
        border-top-right-radius: 35px;
        border-bottom-right-radius: 35px;
    }

    .habit-remind div {
        height: 50%;
        display: flex;
        flex-direction: row;
    }

    



</style>