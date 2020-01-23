/*
    Copyright (C) 2019  apex (full notice in main.js)
 */

import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import Home from '../views/Home.vue'
import Search from '../views/Search.vue'
import Profile from '../views/Profile.vue'
import Internship from "../views/Internship"

import Admin from '../views/Admin'
import Add from '../views/Add'
import Database from '../views/Database'
import Edit from '../views/Edit'
import Analytics from '../views/Analytics'

import store from '../store/index'
import api from '../components/API'

Vue.use(VueRouter);

const routes = [
  {
    path: '/login',
    name: 'login',
    component: Login,
    meta: {
      auth: false,
      admin: false,
      title: 'Login'
    },
    beforeEnter(to, from, next) {
      if(store.getters.isAuthenticated) {
        next({name: 'home'});
      } else {
        next();
      }

    }
  },
  {
    path: '/',
    name: 'home',
    component: Home,
    meta: {
      auth: true,
      admin: false,
      title: 'Home'
    }
  },
  {
    path: '/search',
    name: 'search',
    component: Search,
    meta: {
      auth: true,
      admin: false,
      title: 'Search'
    }
  },
  {
    path: '/internship/:id',
    name: 'internship',
    component: Internship,
    props: true,
    meta: {
      auth: true,
      admin: false,
      title: 'Internship'
    }
  },
  {
    path: '/profile',
    name: 'profile',
    component: Profile,
    meta: {
      auth: true,
      admin: false,
      title: 'Profile'
    }
  },
  {
    path: '/admin',
    name: 'admin',
    component: Admin,
    meta: {
      auth: true,
      admin: true,
      title: 'Admin | Dashboard'
    }
  },
  {
    path: '/admin/add',
    name: 'add',
    component: Add,
    meta: {
      auth: true,
      admin: true,
      title: 'Admin | Add'
    }
  },
  {
    path: '/admin/database',
    name: 'database',
    component: Database,
    meta: {
      auth: true,
      admin: true,
      title: 'Admin | Database'
    }
  },
  {
    path: '/admin/analytics',
    name: 'analytics',
    component: Analytics,
    meta: {
      auth: true,
      admin: true,
      title: 'Admin | Analytics'
    }
  },
  {
    path: '/admin/database/edit/:id',
    name: 'edit',
    component: Edit,
    props: true,
    meta: {
      auth: true,
      admin: true,
      title: 'Admin | Edit'
    }
  },
  {
    path: '*',
    name: 'notfound',
    component: () => import(/* webpackChunkName: "analytics" */ '../views/NotFound.vue'),
    meta: {
      auth: true,
      admin: false,
      title: 'Page Not Found'
    }
  }

];

const router = new VueRouter({
  mode: 'history',
  base: '/pidb/',
  routes
});


router.beforeEach((to, from, next) => {
  if(to.matched.some(record => record.meta.auth)) {
    if(localStorage.getItem("token")) {
      if (!atob(localStorage.getItem("token").split('.')[1]).toString().includes("sub")) {
        next({name: 'login'})
      } else {
        next()
      }
    } else {
      next({name: 'login'})
    }
  } else {
    next()
  }
});

router.beforeEach((to, from, next) => {
  if(to.matched.some(record => record.meta.admin)) {
    if(!JSON.parse(atob(localStorage.getItem("token").split('.')[1]))
        .roles.some(item => item.authority === "ROLE_ADMIN")) {
      next({name: 'home'})
    } else {
      next()
    }
  } else {
    next()
  }
});

router.beforeEach((to, from, next) => {
  if(to.name !== 'login') {
    let token = localStorage.getItem("token");

    let params = {
      "token": token
    };

    if (token !== null) {
      api.checkToken(params).then(res => {
        if (res.data === true)
          next();
      }).catch(() => {
        store.dispatch('logout').catch(() => {
        });
        //router.push({path: '/login'}).catch(() => {});
      });
    } else {
      next();
    }
  }else {
    next();
  }
});


export default router
