import { Injectable } from '@angular/core';

@Injectable()
export class LoginService {

    isLoggedIn = false;

    login(username, password) {
        if (username === "delay" && password === "123")
            this.isLoggedIn = true;
        else
            this.isLoggedIn = false;
        return this.isLoggedIn;
    }

    logout(){
        this.isLoggedIn = false;
        return this.isLoggedIn;
    }
}