import { IProje } from 'app/shared/model/proje.model';
import { ICalisan } from 'app/shared/model/calisan.model';

export interface IBolge {
    id?: number;
    ad?: string;
    adres?: string;
    projes?: IProje[];
    bolgeSorumlu?: ICalisan;
}

export class Bolge implements IBolge {
    constructor(public id?: number, public ad?: string, public adres?: string, public projes?: IProje[], public bolgeSorumlu?: ICalisan) {}
}
