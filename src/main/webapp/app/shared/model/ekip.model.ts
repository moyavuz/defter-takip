import { IIscilik } from 'app/shared/model/iscilik.model';
import { ICalisan } from 'app/shared/model/calisan.model';

export interface IEkip {
    id?: number;
    ad?: string;
    telefon?: string;
    eposta?: string;
    taseronMu?: boolean;
    calisanEkips?: IIscilik[];
    sorumlus?: ICalisan[];
    calisans?: ICalisan[];
}

export class Ekip implements IEkip {
    constructor(
        public id?: number,
        public ad?: string,
        public telefon?: string,
        public eposta?: string,
        public taseronMu?: boolean,
        public calisanEkips?: IIscilik[],
        public sorumlus?: ICalisan[],
        public calisans?: ICalisan[]
    ) {
        this.taseronMu = this.taseronMu || false;
    }
}
