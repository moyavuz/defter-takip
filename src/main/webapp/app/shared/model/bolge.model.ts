import { ICalisan } from 'app/shared/model/calisan.model';

export interface IBolge {
    id?: number;
    ad?: string;
    adres?: string;
    sorumlu?: ICalisan;
}

export class Bolge implements IBolge {
    constructor(public id?: number, public ad?: string, public adres?: string, public sorumlu?: ICalisan) {}
}
