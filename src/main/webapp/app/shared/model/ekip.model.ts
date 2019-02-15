import { IIscilik } from 'app/shared/model/iscilik.model';
import { IMalzemeTakip } from 'app/shared/model/malzeme-takip.model';
import { ICalisan } from 'app/shared/model/calisan.model';
import { IBolge } from 'app/shared/model/bolge.model';

export const enum CalismaTuru {
    MAASLI = 'MAASLI',
    GOTURU = 'GOTURU',
    YEVMIYE = 'YEVMIYE',
    TASERE = 'TASERE',
    GECICI = 'GECICI'
}

export interface IEkip {
    id?: number;
    ad?: string;
    telefon?: string;
    eposta?: string;
    calismaTuru?: CalismaTuru;
    isciliks?: IIscilik[];
    malzemeTakips?: IMalzemeTakip[];
    ekipSorumlu?: ICalisan;
    bolge?: IBolge;
    ekipCalisans?: ICalisan[];
}

export class Ekip implements IEkip {
    constructor(
        public id?: number,
        public ad?: string,
        public telefon?: string,
        public eposta?: string,
        public calismaTuru?: CalismaTuru,
        public isciliks?: IIscilik[],
        public malzemeTakips?: IMalzemeTakip[],
        public ekipSorumlu?: ICalisan,
        public bolge?: IBolge,
        public ekipCalisans?: ICalisan[]
    ) {}
}
