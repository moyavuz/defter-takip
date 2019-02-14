import { ICalisan } from 'app/shared/model/calisan.model';
import { IProje } from 'app/shared/model/proje.model';

export interface IBolge {
    id?: number;
    ad?: string;
    adres?: string;
    calisan?: ICalisan;
    bolges?: IProje[];
}

export class Bolge implements IBolge {
    constructor(public id?: number, public ad?: string, public adres?: string, public calisan?: ICalisan, public bolges?: IProje[]) {}
}
