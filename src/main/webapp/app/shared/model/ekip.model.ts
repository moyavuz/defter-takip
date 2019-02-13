import { ICalisan } from 'app/shared/model/calisan.model';

export interface IEkip {
    id?: number;
    ad?: string;
    telefonNo?: string;
    epostaAdresi?: string;
    taseronMu?: boolean;
    aktifMi?: boolean;
    sorumlu?: ICalisan;
    calisan?: ICalisan;
}

export class Ekip implements IEkip {
    constructor(
        public id?: number,
        public ad?: string,
        public telefonNo?: string,
        public epostaAdresi?: string,
        public taseronMu?: boolean,
        public aktifMi?: boolean,
        public sorumlu?: ICalisan,
        public calisan?: ICalisan
    ) {
        this.taseronMu = this.taseronMu || false;
        this.aktifMi = this.aktifMi || false;
    }
}
