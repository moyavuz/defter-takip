import { IIscilik } from 'app/shared/model/iscilik.model';
import { IMalzemeTakip } from 'app/shared/model/malzeme-takip.model';
import { IPersonel } from 'app/shared/model/personel.model';
import { IBolge } from 'app/shared/model/bolge.model';

export const enum PersonelTuru {
    MAASLI = 'MAASLI',
    GOTURU = 'GOTURU',
    YEVMIYE = 'YEVMIYE'
}

export interface IEkip {
    id?: number;
    ad?: string;
    telefon?: string;
    eposta?: string;
    turu?: PersonelTuru;
    isciliks?: IIscilik[];
    malzemeTakips?: IMalzemeTakip[];
    ekipSorumlu?: IPersonel;
    bolge?: IBolge;
    ekipPersonels?: IPersonel[];
}

export class Ekip implements IEkip {
    constructor(
        public id?: number,
        public ad?: string,
        public telefon?: string,
        public eposta?: string,
        public turu?: PersonelTuru,
        public isciliks?: IIscilik[],
        public malzemeTakips?: IMalzemeTakip[],
        public ekipSorumlu?: IPersonel,
        public bolge?: IBolge,
        public ekipPersonels?: IPersonel[]
    ) {}
}
