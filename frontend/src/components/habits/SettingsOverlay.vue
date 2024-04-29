<script setup>
    import { ref } from 'vue'
    import axios from 'axios'

    const apiPath = "http://localhost:8080/api/habits";

    const emit = defineEmits(['closeSettingsOverlay'])
    const emitCloseSettingsOverlay = () => {
        emit('closeSettingsOverlay');
    }

    const { habit } = defineProps(['habit']); 
    
    const errorObj = ref({
        name: null,
        numReps: null,
        durationTime: null,
        period: null,
        color: null, 
        icon: null,

    });

    const numRepsRef = ref(habit.numReps);
    const durationTimeRef = ref(habit.durationTime);



    const checkHabit = (checkHabit) => {
        if(checkHabit.habitName === null || checkHabit.habitName.length < 5){
            errorObj.value.name = "Habit name needs to be at least 5 characters"
            return false;
        } else {
            errorObj.value.name = null;
        }

        if(checkHabit.numReps === null || checkHabit.numReps <= 0){
            errorObj.value.numReps = "At least 1 rep"
            return false;
        } else {
            errorObj.value.numReps = null;
        }

        if(checkHabit.durationTime == null || checkHabit.durationTime <= 0){
            errorObj.value.durationTime = "Duration can not be 0"
            return false;
        } else {
            errorObj.value.durationTime = null;
        }

        if(checkHabit.period === null){
            errorObj.value.period = "Choose period"
            return false;
        } else {
            errorObj.value.period = null;
        }

        if(checkHabit.color === null){
            errorObj.value.color = "Choose color"
            return false;
        } else {
            errorObj.value.color = null;
        }

        if(checkHabit.icon === null){
            errorObj.value.icon = "Choose icon"
            return false;
        } else {
            errorObj.value.icon = null;
        }

        return true;
    }



    async function updateHabit(habitUpdate){
        habitUpdate.numReps = parseInt(numRepsRef.value);
        habitUpdate.durationTime = parseInt(durationTimeRef.value);
        if(checkHabit(habitUpdate)){
            try {
                await axios.post(`${apiPath}/save`, habitUpdate);
            } catch (error) {
                console.error(error);
            }
            emitCloseSettingsOverlay();
        } else {
            alert("Error, invalid data.");
        }
        
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
        <div class="update-habit-panel">
            <div class="update-habit-title-bar">
                Change Settings
            </div>
            <div class="close-button">
                <button @click="emitCloseSettingsOverlay"><p>+</p></button>
            </div>
            <div class="form">
                <form >

                <div class="form-group">
                    <label for="habitName">
                        Habit Name
                    </label>
                    
                    <input type="text" id="habitName" 
                    v-model.trim="habit.habitName">
                    <p v-if="errorObj.name" class="error">{{ errorObj.name }}</p>
                </div>

                <div class="form-group">
                    <label for="description">
                        Description 
                    </label>
                    <textarea 
                    name="description" id="description" 
                    cols="30" rows="10"
                    v-model.trim="habit.description">
                    </textarea>
                </div>

                

                <div class="sub-container">
                    <div class="form-group">
                        <label for="numReps">
                            Number of reps
                        </label>
                        <input type="number" id="numReps" min="0"
                        v-model="numRepsRef">
                        <p v-if="errorObj.numReps" class="error">{{ errorObj.numReps }}</p>
                    </div>

                    <div class="form-group">
                        <label for="durationTime">
                            Duration time (min)
                        </label>
                        <input type="number" id="durationTime" min="0"
                        v-model="durationTimeRef">
                        <p v-if="errorObj.durationTime" class="error">{{ errorObj.durationTime }}</p>
                    </div>
                </div>

                <div class="sub-container">
                    <div class="form-group">
                        <label for="period">
                            Period (Daily/Weekly)
                        </label>
                        <select name="period" id="period" v-model="habit.period">
                            <option value="DAILY">Daily</option>
                            <option value="WEEKLY">Weekly</option>
                        </select>
                        <p v-if="errorObj.period" class="error">{{ errorObj.period }}</p>
                    </div>

                    <div class="form-group">
                        <label for="color">
                            Color
                        </label>
                        <select name="color" id="color" v-model="habit.color">
                            <option value="GREEN">Green</option>
                            <option value="BLUE">Blue</option>
                            <option value="PINK">Pink</option>
                            <option value="YELLOW">Yellow</option>
                            <option value="PURPLE">Purple</option>
                        </select>
                        <p v-if="errorObj.color" class="error">{{ errorObj.color }}</p>
                    </div>

                    <div class="form-group">
                        <label for="icon">
                            Icon
                        </label>
                        <select name="icon" id="icon" v-model="habit.icon">
                            <option value="ICON1">
                                <p>⚽</p>
                            </option>
                            <option value="ICON2">
                                <p>🎤</p>
                            </option>
                            <option value="ICON3">
                                <p>🎨</p>
                            </option>
                            <option value="ICON4">
                                <p>🎸</p>
                            </option>
                            <option value="ICON5">
                                <p>📖</p>
                            </option>
                        </select>
                        <p v-if="errorObj.icon" class="error">{{ errorObj.icon }}</p>
                    </div>
                </div>

                <div class="sub-container">
                    <div class="form-group check">
                        <input type="checkbox" id="remind" v-model="habit.remind">
                        <label for="remind">
                            Remind
                        </label>
                    </div>

                    <div class="form-group check">
                        <input type="checkbox" id="active" v-model="habit.active">
                        <label for="active">
                            Active
                        </label>
                    </div>
                </div>
                

                </form>

                <div class="update-habit">
                    <button @click="updateHabit(habit)">Save changes</button>
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
        /* background-color: rgba(0, 0, 0, 0.6); */
        z-index: 15;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .update-habit-panel{
        width: 600px;
        background-color: #181818;
        border: 3px solid v-bind(theme);
        border-radius: 10px;
        padding: 30px;
        position: relative;
        display: flex;
        flex-direction: column;
        box-shadow: 0 0 25px 20px rgb(12, 12, 12, 0.8);
    }

    .update-habit-title-bar{
        font-size: 25px;
    }

    .form-group{
        display: flex;
        flex-direction: column;
        margin-top: 10px;
    }

    input[type="text"],
    input[type="number"],
    input[type="color"],
    textarea,
    select,
    button{
        font-size: 16px;
        appearance: none;
        background-color: v-bind(theme);
        border: none;
        border-radius: 5px;
        padding: 5px;
    }

    input::-webkit-outer-spin-button,
    input::-webkit-inner-spin-button {
        -webkit-appearance: none;
        appearance: none;
        margin: 0;
    }

    

    .close-button{
        position: absolute;
        display: relative;
        top: 25px;
        right: 25px;
        z-index: 3;
    }

    .close-button button{
        appearance: none;
        width: 30px;
        height: 30px;
        font-size: 25px;
        background-color: v-bind(theme);
        border-radius: 15px;
        cursor: pointer;
        
    }

    .close-button button p {
        position: relative;
        bottom: 4px;
        transform: rotate(45deg);
    }

    .update-habit button{
        clear: both;
        width: 100%;
        margin: 20px auto 0;
        appearance: none;
        padding: 12px;
        font-size: 20px;
        cursor: pointer;
    }

    button{
        transition: all 200ms;
    }

    button:hover{
        transform: scale(1.05);
        transition: all 200ms;
    }
    
    .check{
        justify-content: center;
        width: 50%;
        flex-direction: row;
        flex-wrap: nowrap;
    }
    
    .sub-container{
        display: flex;
    }

    .sub-container div{
        width: 100%;
    }

    .sub-container input[type="color"],
    .sub-container input[type="number"],
    .sub-container select{
        width: 95%;
        height: 40px;
        margin: 0 auto;
    }

    textarea{
        resize: none;
        width: 100%;
    }

    option,
    select{
        font-size: 20px;
        text-align: center;
    }
    
    .error{
        font-size: 12px;
        color: #b84242;
    }
    


</style>