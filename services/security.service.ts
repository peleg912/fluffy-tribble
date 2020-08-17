import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { LoginService } from './login.service';
import { Injectable } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { LoginComponent } from '../components/login/login.component';

@Injectable({
  providedIn: 'root'
})
export class SecurityService {

  constructor(private log: LoginService, private router: Router, private snack: MatSnackBar) { }

  error1: string = "Unautorized login";
  error2: string = "Not Allowed!";
  error3: string = "Go to login!";



  checkErrors( error : string){
    if (error == this.error1 || error == this.error2 || error == this.error3){
      sessionStorage.clear();
      this.log.logOut().subscribe((text)=>{console.log("user is out due to security issue!");
      location.replace("http://localhost:8080/home");  sessionStorage.OOPS = "Y"; });   
    } else{
      return;
    }

  }

  forcedLogout(){
    sessionStorage.clear();
    sessionStorage.OOPS = "Y";
    this.log.logOut().subscribe((text)=>{console.log("user is out due to security issue!");
    location.replace("http://localhost:8080/home");
  } );
}



   
  

}
