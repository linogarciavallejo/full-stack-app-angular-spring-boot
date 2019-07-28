import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from "@angular/forms";
import { HttpClient, HttpResponse, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable } from "rxjs/Rx";
import "rxjs/add/operator/map";
import "rxjs/add/operator/catch";


@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {

    constructor(private http: HttpClient) { };

    private baseUrl: string = 'http://localhost:8080';
    private getUrl: string = this.baseUrl + '/room/reservation/v1?checkin=2019-03-01&checkout=2019-03-31';
    private postUrl: string = this.baseUrl + '/room/reservation/v1';
    public submitted: boolean;
    roomsearch: FormGroup;
    rooms: Room[];
    request: ReserveRoomRequest;
    currentCheckInVal: string;
    currentCheckOutVal: string;

    ngOnInit() {
        this.roomsearch = new FormGroup({
            checkin: new FormControl(''),
            checkout: new FormControl('')
        });

        const roomsearchValueChanges$ = this.roomsearch.valueChanges;

        roomsearchValueChanges$.subscribe(valChange => {
            this.currentCheckInVal = valChange.checkin;
            this.currentCheckOutVal = valChange.checkout;
        }
        )

    }


    onSubmit({value, valid}: { value: Roomsearch, valid: boolean }) {
        this.getAll()
            .subscribe(
            rooms => {
                this.rooms = rooms;
                console.log(this.rooms);
            },
            err => {
                console.log(err);
            }
            );
    }

    reserveRoom(value: string) {
        this.request = new ReserveRoomRequest(value, this.currentCheckInVal, this.currentCheckOutVal);
        //console.log("Room id for reservation: " + value);

        this.createReservation(this.request);
    }

    getAll(): Observable<Room[]> {
        /*return this.http.get(this.baseUrl + '/room/reservation/v1?checkin=2019-03-01&checkout=2019-03-31')
             .pipe(map(data => data));
             /*.map(this.mapRoom);*/
        return this.http.get<any>(this.getUrl);
        /*return this.http.get(this.baseUrl + '/room/reservation/v1?checkin=2019-03-01&checkout=2019-03-31')
            .map(this.mapRoom);*/
    }

    createReservation(body: ReserveRoomRequest) {
        let bodyString = JSON.stringify(body);
        let headers = {headers : new HttpHeaders({ 'Content-Type': 'application/json' })};
        //let option = new RequestOptions({ headers: headers });

        this.http.post(this.postUrl, body, headers)
            .subscribe(res => console.log(res));
    }

    /*mapRoom(response: HttpResponse<any>): Room[] {
        return response.content;
    }*/
}

export interface Roomsearch {
    checkin: string;
    checkout: string;
}

export interface Room {
    id: string;
    roomNumber: string;
    price: string;
    links: string;
}

export class ReserveRoomRequest {
    roomId: string;
    checkin: string;
    checkout: string;

    constructor(
        roomId: string,
        checkin: string,
        checkout: string
    ) {
        this.roomId = roomId;
        this.checkin = checkin;
        this.checkout = checkout;
    }


}

