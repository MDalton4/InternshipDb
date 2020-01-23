/*
    Copyright (C) 2019  apex (full notice in main.js)
 */

import axios from 'axios'
//import store from '../store/index'

const AXIOS = axios.create({
    //baseURL: 'http://localhost:8081/api',
    baseURL: 'http://cs.merrimack.edu:8080/internshipdb-1.0.0/api',
    headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
    },
});


export default {
    addInternship(json) {
        return AXIOS.post('/add/internship', json, {headers : {Authorization: 'Bearer ' + localStorage.getItem("token")}});//
    },
    addTag(json) {
        return AXIOS.post('/add/tag', json, {headers : {Authorization: 'Bearer ' + localStorage.getItem("token")}});//
    },
    addToFavorites(json){
        return AXIOS.post('/add/user/favorite', json, {headers : {Authorization: 'Bearer ' + localStorage.getItem("token")}});//
    },

    getAllTags(){
        return AXIOS.get('/tag/get/all', {headers : {Authorization: 'Bearer ' + localStorage.getItem("token")}});//
    },
    getAllInternships(){
        return AXIOS.get('/internship/get/all', {headers : {Authorization: 'Bearer ' + localStorage.getItem("token")}});
    },
    getAllUsers(){
        return AXIOS.get('/user/get/all', {headers : {Authorization: 'Bearer ' + localStorage.getItem("token")}});//
    },

    getInternshipById(id) {
        return AXIOS.get('/internship/get/'+id, {headers : {Authorization: 'Bearer ' + localStorage.getItem("token")}});//
    },
    getInterestTags(){
        return AXIOS.get('/tag/get/interests', {headers : {Authorization: 'Bearer ' + localStorage.getItem("token")}});//
    },
    getUserByEmail(email) {
        return AXIOS.get('/user/get/'+email, {headers : {Authorization: 'Bearer ' + localStorage.getItem("token")}});
    },
    getUserFavorites(email) {
        return AXIOS.get('/user/get/favorites/'+email, {headers : {Authorization: 'Bearer ' + localStorage.getItem("token")}});
    },
    getMostRecent() {
        return AXIOS.get('/internship/get/new', {headers : {Authorization: 'Bearer ' + localStorage.getItem("token")}});
    },
    getInterestAnalytics() {
        return AXIOS.get('/tag/get/interests/analytics', {headers : {Authorization: 'Bearer ' + localStorage.getItem("token")}})
    },

    promoteUser(id){
        return AXIOS.get('/user/promote/'+id, {headers : {Authorization: 'Bearer ' + localStorage.getItem("token")}});
    },
    demoteUser(id){
        return AXIOS.get('/user/demote/'+id, {headers : {Authorization: 'Bearer ' + localStorage.getItem("token")}});
    },

    resetAnalytics() {
        return AXIOS.get('/reset/tags', {headers : {Authorization: 'Bearer ' + localStorage.getItem("token")}});
    },

    saveEditInternship(id, json) {
        return AXIOS.post('/edit/internship/'+id, json, {headers : {Authorization: 'Bearer ' + localStorage.getItem("token")}});
    },

    deleteInternship(id) {
        return AXIOS.get('/delete/internship/'+id, {headers : {Authorization: 'Bearer ' + localStorage.getItem("token")}});
    },
    deleteTag(id){
        return AXIOS.get('/delete/tag/'+id, {headers : {Authorization: 'Bearer ' + localStorage.getItem("token")}});
    },
    deleteUser(id){
        return AXIOS.get('/delete/user/'+id, {headers : {Authorization: 'Bearer ' + localStorage.getItem("token")}});
    },
    removeFromFavorites(json){
        return AXIOS.post('/remove/user/favorite', json, {headers : {Authorization: 'Bearer ' + localStorage.getItem("token")}});
    },

    updateAreaOfInterest(interest){
        return AXIOS.post('/edit/interest', interest, {headers : {Authorization: 'Bearer ' + localStorage.getItem("token")}});
    },

    login(json){
        return AXIOS.post('/account/login', json);
    },
    checkToken(params) {
        return AXIOS.get('/account/checktoken', {params, headers : {Authorization: 'Bearer ' + localStorage.getItem("token")}});
    },

    search(params){
        return AXIOS.get('/search', {params, headers : {Authorization: 'Bearer ' + localStorage.getItem("token")}});
    },
    filter(params){
        return AXIOS.get('/filter', {params, headers : {Authorization: 'Bearer ' + localStorage.getItem("token")}});
    }
}
