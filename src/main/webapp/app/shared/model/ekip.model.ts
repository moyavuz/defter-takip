import { ICalisan } from 'app/shared/model/calisan.model';
import { IIscilik } from 'app/shared/model/iscilik.model';

export interface IEkip {
    id?: number;
    ad?: string;
    telefon?: string;
    eposta?: string;
    taseronMu?: boolean;
    sorumlus?: ICalisan[];
    calisans?: ICalisan[];
    iscilik?: IIscilik;
}

export class Ekip implements IEkip {
    constructor(
        public id?: number,
        public ad?: string,
        public telefon?: string,
        public eposta?: string,
        public taseronMu?: boolean,
        public sorumlus?: ICalisan[],
        public calisans?: ICalisan[],
        public iscilik?: IIscilik
    ) {
        this.taseronMu = this.taseronMu || false;
    }
}
