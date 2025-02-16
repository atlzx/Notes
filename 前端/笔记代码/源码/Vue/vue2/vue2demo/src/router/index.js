import Vue from "vue";
import Router from "vue-router";
import Home from "../components/Home.vue";
import SlotInsert from '../components/slot/SlotInsert.vue';
import BaseComponent from "../components/component/BaseComponent.vue";

Vue.use(Router);

export default new Router({
  routes: [
    {
        path: "/",
        name: "Home",
        component: Home
    },
    {
      path:'/baseComponent',
      name:'/baseComponent',
      component:BaseComponent
    },
    {
        path:'/slotDemo',
        name:'/slotDemo',
        component:SlotInsert
    }
  ],
});
