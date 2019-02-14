import { ICalisan } from 'app/shared/model/calisan.model';
import { IProje } from 'app/shared/model/proje.model';

export interface IBolge {
    id?: number;
    ad?: string;
    adres?: string;
    sorumlus?: ICalisan[];
    proje?: IProje;
}

export class Bolge implements IBolge {
    constructor(public id?: number, public ad?: string, public adres?: string, public sorumlus?: ICalisan[], public proje?: IProje) {}
}
