import { ICalisan } from 'app/shared/model/calisan.model';

export interface IBolge {
    id?: number;
    ad?: string;
    adres?: string;
    bolgeSorumlu?: ICalisan;
}

export class Bolge implements IBolge {
    constructor(public id?: number, public ad?: string, public adres?: string, public bolgeSorumlu?: ICalisan) {}
}
