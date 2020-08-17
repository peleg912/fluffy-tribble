import { ClientType } from './../models/coupon';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private client: HttpClient) { }

  login(email:string, password: string, type:ClientType){
    return this.client.post("http://localhost:8080/login/login/" + email + "/" + password + "/" + type, null, {responseType:'text'});
  }

  logOut(){
    return this.client.post("http://localhost:8080/login/logOut/" + sessionStorage.token, null, {responseType:'text'});

  }
}
