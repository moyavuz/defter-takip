import { ICalisan } from 'app/shared/model/calisan.model';

export interface IBolge {
    id?: number;
    adi?: string;
    adresi?: string;
    aktifMi?: boolean;
    sorumlu?: ICalisan;
}

export class Bolge implements IBolge {
    constructor(public id?: number, public adi?: string, public adresi?: string, public aktifMi?: boolean, public sorumlu?: ICalisan) {
        this.aktifMi = this.aktifMi || false;
    }
}
