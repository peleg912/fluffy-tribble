import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { SecurityService } from 'src/app/services/security.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  constructor(private router: Router, private security: SecurityService, private snack: MatSnackBar) { }

  ngOnInit(): void {
    if(sessionStorage.type != "A" ){
      this.snack.open("You can't be here!","❌", {duration:4000});
      this.security.forcedLogout();
   
    } 
  }

  goToCompHandling(){
    this.router.navigate(['/compHandler']);
  }

  goToCustCare(){
    this.router.navigate(['/custCare']);
  }
}
