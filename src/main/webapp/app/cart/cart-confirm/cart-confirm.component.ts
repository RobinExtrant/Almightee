import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Command } from 'app/shared/model/command.model';
import { CommandService } from '../../entities/command/command.service';

@Component({
    selector: 'jhi-cart-confirm',
    templateUrl: './cart-confirm.component.html',
    styles: []
})
export class CartConfirmComponent implements OnInit {
    private command: Command;
    private id: number;

    constructor(private route: ActivatedRoute, private commandService: CommandService) {}

    ngOnInit() {
        this.route.params.subscribe((params: Params) => {
            this.id = params['id'];
        });
        this.commandService.find(this.id).subscribe(res => (this.command = res));

        console.log(this.command.status);
    }
}
