import { Component, OnInit } from '@angular/core';

declare var $: any;

@Component({
    moduleId: module.id,
    selector: 'e2nw-app',
    template: `<router-outlet></router-outlet>`
    //templateUrl: 'app.component.html'
})
export class AppComponent implements OnInit{

    ngOnInit(): void {}
}
