import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { SecurityService } from 'src/app/services/security.service';

@Component({
  selector: 'app-customer-care',
  templateUrl: './customer-care.component.html',
  styleUrls: ['./customer-care.component.css']
})
export class CustomerCareComponent implements OnInit {


  constructor(private router: Router, private snack: MatSnackBar, private security: SecurityService) { }

  ngOnInit(): void {
    if(sessionStorage.type != "A" ){
      this.snack.open("You can't be here!","❌", {duration:4000}).afterDismissed().subscribe(()=> this.security.forcedLogout());
    } 
  }

  goToAdminMenu(){
    this.router.navigate(["/admin"]);
  }

}
