<script setup>
    import { ref } from 'vue'
    import axios from 'axios'

    const apiPath = "http://localhost:8080/api/habits";

    const emit = defineEmits(['showOverlay'])
    const emitCloseOverlay = () => {
        emit('showOverlay');
    }
    
    const errorObj = ref({
        name: null,
        numReps: null,
        durationTime: null,
        period: null,
        color: null, 
        icon: null,

    });

    const numRepsRef = ref(0);
    const durationTimeRef = ref(0);

    const newHabit = ref({
            id: null,
            habitName: null,
            description: null,
            numReps: 0,
            durationTime: 0,
            period: null,
            remind: false,
            color: null, 
            icon: null,
            created: null,
            active: false
        }
    );


    const checkHabit = (habit) => {
        if(habit.habitName === null || habit.habitName.length < 5){
            errorObj.value.name = "Habit name needs to be at least 5 characters"
            return false;
        } else {
            errorObj.value.name = null;
        }

        if(habit.numReps === null || habit.numReps <= 0){
            errorObj.value.numReps = "At least 1 rep"
            return false;
        } else {
            errorObj.value.numReps = null;
        }

        if(habit.durationTime == null || habit.durationTime <= 0){
            errorObj.value.durationTime = "Duration can not be 0"
            return false;
        } else {
            errorObj.value.durationTime = null;
        }

        if(habit.period === null){
            errorObj.value.period = "Choose period"
            return false;
        } else {
            errorObj.value.period = null;
        }

        if(habit.color === null){
            errorObj.value.color = "Choose color"
            return false;
        } else {
            errorObj.value.color = null;
        }

        if(habit.icon === null){
            errorObj.value.icon = "Choose icon"
            return false;
        } else {
            errorObj.value.icon = null;
        }

        return true;
    }



    async function save(habitSave){
        habitSave.numReps = parseInt(numRepsRef.value);
        habitSave.durationTime = parseInt(durationTimeRef.value);
        if(checkHabit(habitSave)){
            
            try {
                await axios.post(`${apiPath}/save`, habitSave);
            } catch (error) {
                console.error(error);
            }
            emitCloseOverlay();
        } else {
            alert("Error, invalid data.");
        }
        
    }
    
    

</script>

<template>
    <div class="overlay">
        <div class="add-habit-panel">
            <div class="add-habit-title-bar">
                Add New Habit
            </div>
            <div class="close-button">
                <button @click="emitCloseOverlay"><p>+</p></button>
            </div>
            <div class="form">
                <form >

                <div class="form-group">
                    <label for="habitName">
                        Habit Name
                    </label>
                    
                    <input type="text" id="habitName" 
                    v-model.trim="newHabit.habitName">
                    <p v-if="errorObj.name" class="error">{{ errorObj.name }}</p>
                </div>

                <div class="form-group">
                    <label for="description">
                        Description 
                    </label>
                    <textarea 
                    name="description" id="description" 
                    cols="30" rows="10"
                    v-model.trim="newHabit.description">
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
                        <select name="period" id="period" v-model="newHabit.period">
                            <option value="DAILY">Daily</option>
                            <option value="WEEKLY">Weekly</option>
                        </select>
                        <p v-if="errorObj.period" class="error">{{ errorObj.period }}</p>
                    </div>

                    <div class="form-group">
                        <label for="color">
                            Color
                        </label>
                        <select name="color" id="color" v-model="newHabit.color">
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
                        <select name="icon" id="icon" v-model="newHabit.icon">
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
                        <input type="checkbox" id="remind" v-model="newHabit.remind">
                        <label for="remind">
                            Remind
                        </label>
                    </div>

                    <div class="form-group check">
                        <input type="checkbox" id="active" v-model="newHabit.active">
                        <label for="active">
                            Active
                        </label>
                    </div>
                </div>
                

                </form>

                <div class="add-habit">
                    <button @click="save(newHabit)">Add new habit</button>
                </div>
            </div>
            

        </div>
    </div>


</template>


<style scoped>

    .overlay{
        position: absolute;
        min-width: 1200px;
        width: 100%;
        height: 85%;
        max-height: calc(100% - 90px);
        z-index: 10;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .add-habit-panel{
        width: 600px;
        background-color: #181818;
        border: 3px solid #42b883;;
        border-radius: 10px;
        padding: 30px;
        position: relative;
        display: flex;
        flex-direction: column;
        box-shadow: 0 0 25px 20px rgb(12, 12, 12, 0.8);
    }

    .add-habit-title-bar{
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
        background-color: #42b883;
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
        background-color: #42b883;
        border-radius: 15px;
        cursor: pointer;
        
    }

    .close-button button p {
        position: relative;
        bottom: 4px;
        transform: rotate(45deg);
    }

    .add-habit button{
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
        background-color: #37996d;
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