import { ICalisan } from 'app/shared/model/calisan.model';

export interface IEkip {
    id?: number;
    ad?: string;
    telefon?: string;
    eposta?: string;
    taseronMu?: boolean;
    sorumlu?: ICalisan;
    calisan?: ICalisan;
}

export class Ekip implements IEkip {
    constructor(
        public id?: number,
        public ad?: string,
        public telefon?: string,
        public eposta?: string,
        public taseronMu?: boolean,
        public sorumlu?: ICalisan,
        public calisan?: ICalisan
    ) {
        this.taseronMu = this.taseronMu || false;
    }
}
