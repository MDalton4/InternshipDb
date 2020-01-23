/*
    Copyright (C) 2019  apex (full notice in main.js)
 */

import Vue from 'vue'
import Vuex from 'vuex'

//import router from '../router/index'
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

export default new Vuex.Store({
  plugins: [createPersistedState()],
  state: {
    auth: false,
    admin: false,
  },

  mutations: {
    login: (state, payload) => {
        localStorage.setItem("token", payload);

        state.auth = atob(payload.split('.')[1]).toString().includes("sub");
        state.admin = JSON.parse(atob(payload.split('.')[1]))
                      .roles.some(item => item.authority === "ROLE_ADMIN");
    },

    logout: (state) =>  {
      localStorage.clear();

      state.auth = false;
      state.admin = false;
    }
  },

  actions: {
    login ({commit}, payload) {
      commit('login', payload);
    },

    logout ({ commit }) {
      commit('logout');
    },
   },

  getters: {
    isAuthenticated: (state) => {
          return state.auth;
    },
    isAdmin: (state) => {
          return state.admin;
    }
  },
  modules: {
  }
})
