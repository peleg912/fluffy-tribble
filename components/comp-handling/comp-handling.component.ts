import { SecurityService } from './../../services/security.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { FormGroup } from '@angular/forms';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-comp-handling',
  templateUrl: './comp-handling.component.html',
  styleUrls: ['./comp-handling.component.css']
})
export class CompHandlingComponent implements OnInit {

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
