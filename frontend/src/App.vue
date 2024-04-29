<script setup>
  import { RouterView } from 'vue-router'
  import TitleAndNav from './components/TitleAndNav.vue'


</script>

<template>

  <div class="container">
    <TitleAndNav />
    <RouterView />
  </div>

  

</template>

<style>


  input[type="checkbox"]{
    appearance: none;
    width: 20px;
    height: 20px;
    background-color: #b84242;
    border-radius: 5px;
    margin-right: 10px;
    display: flex;
  }

  input[type="checkbox"]:checked{
    background-color: #42b883;
  }

  .container{
    width: 1200px;
    height: 100vh;
    margin: 0 auto;
  }


</style>