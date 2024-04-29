<script>
    import { RouterLink } from 'vue-router';


</script>

<template>
    <div class="bar">
        <div class="title-bar">
            <p>Habit Tracker</p>
        </div>
        <div class="nav">
            <div class="nav-item">
                <RouterLink active-class="active" to="/"><p>Habits</p></RouterLink>
            </div>
            <div class="nav-item">
                <RouterLink active-class="active" to="/achievements"><p>Achievements</p></RouterLink>
            </div>
            <div class="nav-item">
                <RouterLink active-class="active" to="/charts"><p>Charts</p></RouterLink>
            </div>

        </div>
    </div>
    

</template>

<style scoped>
    div{
        display: flex;
    }

    .bar{
        height: 15%;
        width: 100%;
        flex-wrap: wrap;
    }

    .title-bar{
        background-color: #42b883;
        height: 60%;
        width: 100%;
        display: flex;
        justify-content: center;
        align-items: center;

    }

    .title-bar p{
        font-size: 70px;
        font-weight: 900;
        color: #181818;
        
    }

    .nav{
        height: 40%;
        width: 100%;
    }

    .nav-item{
        width: 400px;
        border: 3px solid #42b883;
    }

    .nav-item a{
        width: 100%;
        height: 100%;
        text-decoration: none;
        color: #42b883;
        font-size: 33px;
        display: flex;
        justify-content: center;
        align-items: center;
        transition: all 200ms;
    }


    .nav-item a.active,
    .nav-item a.active p{
        background-color: #42b883;
        color: #181818;
        font-weight: bold;
        transition: all 200ms;
    }



</style>