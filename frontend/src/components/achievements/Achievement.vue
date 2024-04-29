<script setup>
    import { ref } from 'vue';
    const { achievement } = defineProps(['achievement']);
    // console.log(achievement);

    const theme = ref(achievement.color);

    switch(achievement.color){
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

    <div class="achievement">
        <header>
            <div class="emoji-container">
                <div class="emoji">
                    <p>{{ achievement.emoji }}</p>
                </div>
            </div>
            <div class="achievement-title">
                <p>{{ achievement.achievementTitle }}</p>
            </div>
            
        </header>
        <div class="achievement-content">
            <div class="habit-name">
                <p>{{ achievement.habitName }}</p>
            </div>
            <div class="achievement-message">
                <div class="achievement-message-text">
                    <p>{{ achievement.achievementMessage }}</p>
                </div>
            </div>

        </div>

    </div>

</template>

<style scoped>

    div, header{
        display: flex;
    }

    .achievement{
        height: 250px;
        width: 500px;
        margin: 25px 50px 0;
        border: 3px solid v-bind(theme);
        border-radius: 35px;
        flex-wrap: wrap;
    }

    .achievement header {
        height: 25%;
        width: 100%;
        background-color: v-bind(theme);
        border-top-left-radius: 25px;
        border-top-right-radius: 25px;
        
    }

    .emoji-container{
        width: 15%;
        height: 100%;
        justify-content: center;
        align-items: center;
    }

    .emoji{
        width: 75%;
        height: 90%;
        border-radius: 30px;
        background-color: #181818;
        justify-content: center;
        align-items: center;
        color: white;
        font-size: 35px;
    }

    .achievement-title{
        width: 85%;
        height: 100%;
        justify-content: center;
        align-items: center;
    }

    .achievement-title p {
        font-size: 30px;
        font-weight: bold;
        color: black;
    }

    .achievement-content{
        width: 100%;
        height: 75%;
        flex-wrap: wrap;
    }
    
    .achievement-content .habit-name{
        width: 100%;
        height: 25%;
        justify-content: center;
        align-items: center;
        font-size: 25px;
    }

    .achievement-content .achievement-message{
        width: 100%;
        height: 75%;
        padding: 20px;
        padding-top: 0px;
    }

    
    .achievement-message-text{
        height: 100%;
        width: 100%;
        background-color: #222222;
        border-radius: 20px;
        padding: 15px;
        font-size: 20px;
    }



</style>