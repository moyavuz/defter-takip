import { IMalzeme } from 'app/shared/model/malzeme.model';
import { IPoz } from 'app/shared/model/poz.model';
import { IIscilik } from 'app/shared/model/iscilik.model';

export interface IBirim {
    id?: number;
    ad?: string;
    aciklama?: string;
    kisaltma?: string;
    carpan?: number;
    malzeme?: IMalzeme;
    poz?: IPoz;
    iscilik?: IIscilik;
}

export class Birim implements IBirim {
    constructor(
        public id?: number,
        public ad?: string,
        public aciklama?: string,
        public kisaltma?: string,
        public carpan?: number,
        public malzeme?: IMalzeme,
        public poz?: IPoz,
        public iscilik?: IIscilik
    ) {}
}
