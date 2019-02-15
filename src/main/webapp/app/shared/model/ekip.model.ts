import { IIscilik } from 'app/shared/model/iscilik.model';
import { IMalzemeTakip } from 'app/shared/model/malzeme-takip.model';
import { ICalisan } from 'app/shared/model/calisan.model';

export interface IEkip {
    id?: number;
    ad?: string;
    telefon?: string;
    eposta?: string;
    taseronMu?: boolean;
    isciliks?: IIscilik[];
    malzemeTakips?: IMalzemeTakip[];
    ekipCalisans?: ICalisan[];
}

export class Ekip implements IEkip {
    constructor(
        public id?: number,
        public ad?: string,
        public telefon?: string,
        public eposta?: string,
        public taseronMu?: boolean,
        public isciliks?: IIscilik[],
        public malzemeTakips?: IMalzemeTakip[],
        public ekipCalisans?: ICalisan[]
    ) {
        this.taseronMu = this.taseronMu || false;
    }
}
