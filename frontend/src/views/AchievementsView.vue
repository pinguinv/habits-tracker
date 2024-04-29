<script setup>
    import { ref } from 'vue'
    import axios from 'axios'

    import Achievement from '../components/achievements/Achievement.vue'

    const apiPath = "http://localhost:8080/api/habits";
    
    const findHabits = ref([]);
    
    const achievementList = ref([]);
    
    const checkAchievements = (habit) => {
        // console.log(habit);
        const newAchievement = {
            id: 0,
            habitName: null,
            achievementTitle: null,
            achievementMessage: null,
            color: null,
            emoji: null
        };
        checkTitleByReps(habit, newAchievement);
        checkRepsStreak(habit, newAchievement);
        
    };

    const getEmoji = (achievement) => {
        switch(achievement.achievementTitle){
            case "Streak Rookie":
                achievement.emoji = "🔥";
                break;
            case "First Steps":
                achievement.emoji = "👏";
                break;
            case "Streak Adept":
                achievement.emoji = "💥";
                break;
            case "Rookie":
                achievement.emoji = "💪";
                break;
            case "Adept":
                achievement.emoji = "🎯";
                break;
            case "Master":
                achievement.emoji = "🏅";
                break;
            case "Streak Master":
            case "Guru":
                achievement.emoji = "🏆";
                break;
            case "Streak Legend":
                achievement.emoji = "👑";
                break;
            case "":
                achievement.emoji = "🎩👑💡💎💯";
                break;
        }
    }
    

    const checkTitleByReps = (habit, newAchievement) => {
            const nReps = habit.numReps;
            const titleArr = [
                [nReps * 1, "First Steps"],
                [nReps * 5, "Rookie"],
                [nReps * 10, "Adept"],
                [nReps * 20, "Master"],
                [nReps * 35, "Guru"]];
            // console.log(titleArr)
            let i = 0;
            while(habit.habitDoneList.length >= titleArr[i][0]){
                newAchievement = {
                    habitName: habit.habitName,
                    id: habit.id,
                    color: habit.color,
                    achievementTitle: titleArr[i][1],
                    achievementMessage: 
                    `You've done all the repeats for the first period!`
                }
                getEmoji(newAchievement);
                achievementList.value.push(newAchievement);
                
                if(i > 0){
                    newAchievement.achievementMessage = 
                    `You've done ${titleArr[i][0]} repeats of this habit so far!`;
                }
                i++;
            }
            
    };

    const checkRepsStreak = (habit, newAchievement) => {
        // console.log(habit.habitDoneList)
        const firstRepsList = [];
        
        for(let i = 0; i < habit.habitDoneList.length; i++) {
            const done = habit.habitDoneList[i];
            // console.log(done);
            if(done.numRepsDone === 1){
                firstRepsList.push(done)
            }
            
        }
        // console.log(firstRepsList)
        if(firstRepsList.length >= 2){
            let counter = 1;
            for(let i = 1; i < firstRepsList.length; i++ ){
                if(new Date(firstRepsList[i-1].created).getTime() === 
                (new Date(firstRepsList[i].created).getTime() - 86400000)){
                    counter++;
                }
            }

            const titleArr = [
                [3, "Streak Rookie"],
                [7, "Streak Adept"],
                [14, "Streak Master"],
                [21, "Streak Guru"],
                [28, "Streak Legend"]];

            let i = 0;
            while(counter >= titleArr[i][0]){
                newAchievement = {
                    habitName: habit.habitName,
                    id: habit.id + 1000,
                    color: habit.color,
                    achievementTitle: titleArr[i][1],
                    achievementMessage: 
                    `A streak of ${titleArr[i][0]} repeats in a row!`
                }
                getEmoji(newAchievement);
                achievementList.value.push(newAchievement);
                i++;
            }
        }

    };

    async function findAll(){
        try {
            findHabits.value = (await axios.get(`${apiPath}/all`)).data;

            findHabits.value.forEach(habit => {
                // console.log(habit);
                checkAchievements(habit);
            });
        } catch (error) {
            console.error(error);
        }
    }

    findAll();

</script>

<template>

    <div class="achievements-container">

        <Achievement
        v-for="achievement in achievementList"
        :key="achievement.id"
        :achievement="achievement"/>

    </div>

</template>

<style scoped>

    .achievements-container{
        display: flex;
        width: 100%;
        height: 85%;
        align-items: flex-start;
        justify-content: flex-start;
        flex-wrap: wrap;
    }

</style>